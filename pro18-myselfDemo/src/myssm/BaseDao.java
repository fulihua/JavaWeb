package myssm;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/11 6:58 PM
 * @description
 */
public abstract class BaseDao<T>{
    protected Connection conn = null;
    protected PreparedStatement psmt = null;
    protected ResultSet rs = null;
    private String entityName;
    public BaseDao(){
        ParameterizedType parameterizedType =(ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        entityName = types[0].getTypeName();
    }

    protected  Connection getConn(){return TransManager.getConnection();}
    protected void close(ResultSet rs,PreparedStatement psmt,Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (psmt != null) {
                psmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    private void setParams(PreparedStatement psmt,Object... params) throws SQLException{
        if(params!=null&&params.length>0){
            for(int i=0;i<params.length;i++){
                psmt.setObject(i+1,params[i]);
            }
        }
    }
    protected int executeUpdate(String sql,Object...params) {
        sql = sql.trim().toUpperCase();
        conn = getConn();
        boolean insetFlag = sql.startsWith("INSERT INTO");
        try {
            if (insetFlag) {
                psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                psmt = conn.prepareStatement(sql);
            }
            setParams(psmt, params);
            int count = psmt.executeUpdate();
            if (insetFlag) {
                rs = psmt.getGeneratedKeys();
                if (rs.next()) {
                    Long pk = rs.getLong(1);
                    return pk.intValue();
                }
            } else {
                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            close(rs,psmt,conn);
        }
        return 0;
    }

    protected List<T> executeQuery(String sql,Object...params){
        List<T> list = new ArrayList<>();
        conn=getConn();
        try{
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs= psmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Class entityClass = ClassUtil.getClass(entityName);
                @SuppressWarnings("unchecked")
                T instance = (T)ClassUtil.createInstance(entityName);
                for(int i=1;i<=columnCount;i++){
                    String rowcolumn = rsmd.getColumnName(i);
                    String columnName = ClassUtil.underLineToCamel(rowcolumn);
                    Object columnValue = rs.getObject(i);
                    ClassUtil.setProperty(instance,columnName,columnValue);
                }
                list.add(instance);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            close(rs,psmt,conn);
        }
        return list;
    }

    protected T load(String sql,Object...params){
        conn=getConn();
        try {
            psmt= conn.prepareStatement(sql);
            setParams(psmt,params);
            rs= psmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                Class entityClass = ClassUtil.getClass(entityName);
                @SuppressWarnings("unchecked")
                T instance = (T)ClassUtil.createInstance(entityName);
                for(int i=1;i<=columnCount;i++){
                    String rowcolumn = rsmd.getColumnName(i);
                    String columnName = ClassUtil.underLineToCamel(rowcolumn);
                    Object columnValue = rs.getObject(i);
                    ClassUtil.setProperty(instance,columnName,columnValue);
                }
                return instance;
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            close(rs,psmt,conn);
        }
        return null;

    }

    protected ResultSet executeQueryRaw(String sql,Object...params) throws SQLException{
        conn = getConn();
        psmt = conn.prepareStatement(sql);
        setParams(psmt,params);
        return psmt.executeQuery();
    }

}