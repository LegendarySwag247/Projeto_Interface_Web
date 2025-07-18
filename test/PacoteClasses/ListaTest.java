
package PacoteClasses;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;



public class ListaTest {
    
    public ListaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
     
    @Test
    public void TestarFiltroEditar(){
        Lista lista = new Lista(0, "Minha Rotina");
        Task tarefa;
        boolean type = false; // = true;
        
        if(type){
            tarefa = new TaskRotina(1, "Arrumar cama", "ROTINA", "s");
        }else{
            tarefa = new TaskCompromisso(1, "Arrumar cama", "ROTINA", "s", "12:00-23/10/2025");
        }
        int identidadeTarefa = 1;
        String tituloQualquer = "Arrumar cama";
        String tipoQualuer = "ROTINA";
        
        
        assertTrue(lista.FiltroEditar(tarefa, identidadeTarefa, tituloQualquer, tipoQualuer));
        //assertFalse(lista.FiltroEditar(tarefa, identidadeTarefa, tituloQualquer, tipoQualuer))
    }
    @Test
    public void TestarPegarIdTarefa(){
        String titulo = "Arrumar cama";
        String tipo = "ROTINA";
        String concluido = "n";
        String data_horario = "-";
        
        assertEquals(2, Lista.pegarIdTarefa(titulo, tipo, concluido, data_horario));
        
    }
    
}
