/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.application;

/**
 *
 * @author Maryam
 */
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import csa.healthsystem.resource.AppointmentResource;

public class AppointmentApplication extends ResourceConfig {
    public AppointmentApplication() {
        register(AppointmentResource.class);
        
        property(ServerProperties.TRACING, "ALL");
    }
}