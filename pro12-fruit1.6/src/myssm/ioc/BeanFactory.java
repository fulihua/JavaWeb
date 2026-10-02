package myssm.ioc;

/**
 * @author fulihua
 * @date 2026/10/2 5:51 PM
 * @description Bean工厂
 */
public interface BeanFactory {
    Object getBean(String id);
}