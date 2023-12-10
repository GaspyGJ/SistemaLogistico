package SistemaLogistico;

import SistemaLogistico.DTO.Creator;
import UI.VentanaPrincipal;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class SistemaLogisitco {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(SistemaLogisitco.class)
				.headless(false).run(args);
		//se utiliza para crear los DTO en las entidades.
		Creator context = new Creator(ctx);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
					JFrame frame = new VentanaPrincipal();
			}
		});

	}
}
