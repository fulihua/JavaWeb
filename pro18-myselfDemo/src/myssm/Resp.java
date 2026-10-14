package myssm;

/**
 * @author fulihua
 * @date 2026/10/13 5:11 PM
 * @description
 */
    public class Resp {
        private Integer code;
        private String msg;

        public Resp() {
        }

        public Resp(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

