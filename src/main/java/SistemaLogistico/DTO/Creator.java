package SistemaLogistico.DTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Creator {
    private static ApplicationContext context;
    @Autowired
    public Creator(ApplicationContext applicationContext) {
        context = applicationContext;
    }
    public static <T> T getDTO(Class<T> C) {
        return context.getBean(C);
    }
}

