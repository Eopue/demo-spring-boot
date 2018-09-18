package cn.com.demo.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 打为jar包后也能找到配置文件，并以流的方式读取。
 * <p>
 * InputStream is = ClassLoaderUtil.getResourceAsStream("config/others/config.properties",XXX.class);
 * if (null != is) { reader = new InputStreamReader(is, "UTF-8"); }
 *
 * @author Xiaolu.Liu
 */
public class ClassLoaderUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClassLoaderUtil.class);

    public static URL getResource(String resourceName, Class<?> callingClass) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null) {
            url = ClassLoaderUtil.class.getClassLoader().getResource(resourceName);
        }
        if (url == null) {
            ClassLoader cl = callingClass.getClassLoader();
            if (cl != null) {
                url = cl.getResource(resourceName);
            }
        }
        if ((url == null) && (resourceName != null) &&
                ((resourceName.length() == 0) || (resourceName.charAt(0) != '/'))) {
            return getResource('/' + resourceName, callingClass);
        }
        if (url != null) {
            logger.info("配置文件路径为= " + url.getPath());
        }
        return url;
    }

    public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
        URL url = getResource(resourceName, callingClass);
        try {
            return (url != null) ? url.openStream() : null;
        } catch (IOException e) {
            logger.error("配置文件" + resourceName + "没有找到! ", e);
            return null;
        }
    }

    /**
     * 扫描包下所有class
     *
     * @param pack 包路径
     */
    public static Set<Class<?>> scanClassesByPackage(String pack) {


        Set<Class<?>> classes = new LinkedHashSet<>();
        boolean recursive = true;
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findClassesInPackage(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar;
                    try {
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                if (idx != -1) {
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                if ((idx != -1) || recursive) {
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return classes;
    }

    /**
     * 扫描包路径下所有class文件,迭代
     */
    public static void findClassesInPackage(String packageName, String packagePath, final boolean recursive,
                                            Set<Class<?>> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirFiles = dir.listFiles(file -> {
            return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
        });
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findClassesInPackage(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader()
                            .loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }

}