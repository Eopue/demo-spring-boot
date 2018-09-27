package cn.com.demo.shiro.constants;

/**
 * 异常的定义类
 *
 * @author liuxiaolu
 */
public interface RestConst {

    enum HttpConst implements RestEnum {
        OK(200, "200"), CREATED(201, "CREATED"),

        Unauthorized(401, "Unauthorized"), Forbidden(403, "Forbidden");

        private int code;
        private String type;

        HttpConst(int code, String type) {
            this.code = code;
            this.type = type;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return this.type;
        }

    }

    /**
     * 枚举 系统错误.
     * 首位为1
     * 1 + [0000-9999]
     */
    enum SysError implements RestEnum {
        /**
         * 系统错误.
         */
        SYS_ERROR(10001, "SystemError"), /**
         * 服务不存在.
         */
        SERVICE_STOP(10002, "ServiceUnavailable"), /**
         * 远程调用失败.
         */
        RPC_ERROR(10003, "RPCerror"), /**
         * 未知系统错误.
         */
        UNKNOWN_ERROR(19999, "UnknownSystemError");

        private int code;
        private String type;

        SysError(int code, String type) {
            this.code = code;
            this.type = type;
        }

        /**
         * 获得 type.
         *
         * @param code the code
         * @return the type
         */
        public static String getType(int code) {
            for (SysError sysError : SysError.values()) {
                if (sysError.getCode() == code) {
                    return sysError.getType();
                }
            }
            return SYS_ERROR.getType();
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return this.type;
        }
    }


    /**
     * 枚举 业务错误.
     * 首位为2
     * 2 + [0000-9999]
     */
    enum BizError implements RestEnum {
        /**
         * 普通业务异常.
         */
        BIZ_ERROR(20001, "BusinessError"), /**
         * 取得资源不存在.
         */
        RES_NOT_FOUND(20002, "ResourceNotFound"), /**
         * 资源更新失败.
         */
        RES_UPDATE_FAILED(20003, "ResourceUpdateFailed"), /**
         * 资源创建失败.
         */
        RES_CREATE_FAILED(20004, "ResourceCreateFailed"), /**
         * 资源删除失败.
         */
        RES_DELETE_FAILED(20005, "ResourceDeleteFailed"), /**
         * 验证失败.
         */
        VALI_FAILED(20006, "ValidateFailed"), /**
         * 验证失败.
         */
        BAD_PARAM(20007, "BadParameters"), /**
         * 验证失败.
         */
        INSUFFICIENT_RESOURCES(20008, "InsufficientResources"), /**
         * 未知错误.
         */
        UNKNOWN_ERROR(29999, "UnknownBusinessError");

        private int code;
        private String type;

        BizError(int code, String type) {
            this.code = code;
            this.type = type;
        }

        /**
         * 获得 type.
         *
         * @param code the code
         * @return the type
         */
        public static String getType(int code) {
            for (SysError sysError : SysError.values()) {
                if (sysError.getCode() == code) {
                    return sysError.getType();
                }
            }
            return BIZ_ERROR.getType();
        }

        @Override
        public String getType() {
            return this.type;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String toString() {
            return this.type;
        }
    }

    /**
     * 接口 Error enum.
     */
    interface RestEnum {
        /**
         * 获得 type.
         *
         * @return the type
         */
        String getType();

        /**
         * 获得 code.
         *
         * @return the code
         */
        int getCode();
    }
}
