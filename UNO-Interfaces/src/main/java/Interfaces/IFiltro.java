/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Plantilla.ContextoPipeline;



/**
 *
 * @author saula
 */
public interface IFiltro<I, O> {
    
    ContextoPipeline<O> procesar(ContextoPipeline<I> contexto) throws Exception;
}
