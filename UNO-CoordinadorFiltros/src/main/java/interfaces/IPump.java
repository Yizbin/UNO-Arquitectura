/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Plantilla.ContextoPipeline;

/**
 *
 * @author Abraham Coronel
 */
public interface IPump<T, O> {

    void procesar(ContextoPipeline<T> contexto) throws Exception;
}
