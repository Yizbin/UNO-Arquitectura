/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Utilidades;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author saula
 */
public class UtilidadRed {

    private static final String IP_LOCALHOST = "127.0.0.1";

    private UtilidadRed() {
    }

    public static String obtenerIPv4Local() {
        List<String> candidatas = obtenerIPv4Candidatas();

        for (String ip : candidatas) {
            if (ip.startsWith("192.168.")) {
                return ip;
            }
        }

        for (String ip : candidatas) {
            if (ip.startsWith("10.")) {
                return ip;
            }
        }

        for (String ip : candidatas) {
            if (esIPv4Privada172(ip)) {
                return ip;
            }
        }

        if (!candidatas.isEmpty()) {
            return candidatas.get(0);
        }

        return IP_LOCALHOST;
    }

    private static List<String> obtenerIPv4Candidatas() {
        List<String> candidatas = new ArrayList<>();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces != null && interfaces.hasMoreElements()) {
                NetworkInterface interfaz = interfaces.nextElement();

                if (!esInterfazValida(interfaz)) {
                    continue;
                }

                Enumeration<InetAddress> direcciones = interfaz.getInetAddresses();

                while (direcciones.hasMoreElements()) {
                    InetAddress direccion = direcciones.nextElement();

                    if (esDireccionValida(direccion)) {
                        candidatas.add(direccion.getHostAddress());
                    }
                }
            }
        } catch (SocketException ex) {
            return List.of();
        }

        return candidatas;
    }

    private static boolean esInterfazValida(NetworkInterface interfaz) throws SocketException {
        return interfaz != null
                && interfaz.isUp()
                && !interfaz.isLoopback()
                && !interfaz.isVirtual();
    }

    private static boolean esDireccionValida(InetAddress direccion) {
        return direccion instanceof Inet4Address
                && !direccion.isLoopbackAddress()
                && !direccion.isLinkLocalAddress();
    }

    private static boolean esIPv4Privada172(String ip) {
        String[] partes = ip.split("\\.");

        if (partes.length != 4 || !"172".equals(partes[0])) {
            return false;
        }

        try {
            int segundoOcteto = Integer.parseInt(partes[1]);
            return segundoOcteto >= 16 && segundoOcteto <= 31;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
