/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Plantilla.ContextoPipeline;

/**
 *
 * @author Abraham Coronel
 */
public interface IPump<T> {

    void procesar(ContextoPipeline<T> contexto) throws Exception;
}
