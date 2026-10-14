package activitycommit.test;

/**
 * @author fulihua
 * @date 2026/10/12 11:35 AM
 * @description
 */

import myssm.ConnUtil;

import java.sql.Connection;

   /* public class TestConn {
        public static void main(String[] args) {
            try {
                Connection conn = ConnUtil.getConnection();
                System.out.println("✅ 获取连接成功！" + conn);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("❌ 获取连接失败");
            }
        }
    }

    */



public class TestConn {
    public static void main(String[] args) {
        Connection conn = ConnUtil.getConnection();
        System.out.println("直接ConnUtil获取连接："+conn);
    }
}


