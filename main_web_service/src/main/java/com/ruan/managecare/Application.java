package com.ruan.managecare;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application { //implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

        System.out.println("Good Luck, Andrew! I will work for you very well. Your Spring.");
    }

    @Bean
    public EmbeddedServletContainerFactory factory() {
        final String keystoreFile = "/apps/managecare/keystore.p12";
        final String keystorePass = "letmeenter";
        final String keystoreType = "PKCS12";
        final String keystoreProvider = "SunJSSE";
        final String keystoreAlias = "tomcat";

        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector con) {
                con.setPort(8443);
                con.setScheme("https");
                con.setSecure(true);
                Http11NioProtocol proto = (Http11NioProtocol) con.getProtocolHandler();
                proto.setSSLEnabled(true);
                proto.setKeystoreFile(keystoreFile);
                proto.setKeystorePass(keystorePass);
                proto.setKeystoreType(keystoreType);
                proto.setProperty("keystoreProvider", keystoreProvider);
                proto.setKeyAlias(keystoreAlias);
            }
        });
        return factory;
    }

}
