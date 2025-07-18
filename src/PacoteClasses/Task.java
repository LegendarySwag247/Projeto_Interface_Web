
package PacoteClasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;

public abstract class Task {
    int id;
    private String titulo, tipo, concluido;
    
    public Task(int id, String titulo, String tipo, String concluido){
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.concluido = concluido;
    }
    
    public int getId(){
        return id;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return tipo;
    }
    
    public String getConcluido(){
        return concluido;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setConcluido(String concluido) {
        this.concluido = concluido;
    }
    
    //para facilitar a filtracao
    public boolean FiltrarDados(Lista lista, Task task, String Listanome, int taskId){
        return(Listanome.equals(lista.getNome()) && task.getId() == taskId );
    }
    // Excluir dados da lista
    public void exclirDados(String lista_nome, String _titulo, String _tipo){
        //List<Task> tasks = Lista.ListarTask();
        String caminho = "./dados/tarefas.csv"; // Caminho para o arquivo .csv
        StringBuilder conteudoAtualizado = new StringBuilder();
        boolean encontrado = false;
        try{
            FileReader fileReader = new FileReader (caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            boolean primeira = true;// quando ha conversao de dados.
            while((linha = bufferedReader.readLine()) != null){
                if(primeira){
                    primeira = false;
                    conteudoAtualizado.append(linha).append("\n");
                    continue;
                }
                
                String[] partes = linha.split(";");
                //int id = Integer.parseInt(partes[0]);
                String nome = partes[2];
                String Listatitulo = partes[3];
                String Listatipo = partes[4];
                
                if(nome.equals(lista_nome) && Listatitulo.equals(_titulo) && Listatipo.equals(_tipo)){
                    encontrado = true;
                }else{
                    conteudoAtualizado.append(linha).append("\n");
                }
                
            }
            
            if (encontrado){
                FileWriter fileWriter = new FileWriter(caminho);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(conteudoAtualizado.toString());
                bufferedWriter.close();
            }
            
            bufferedReader.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    // Editar daods da lista
    public void editarDados(Lista lista, Task task, String novo_titulo, String novo_tipo, String opcao, String data){
        String caminho = "./dados/tarefas.csv"; // Caminho para o arquivo .csv
        StringBuilder conteudoAtualizado = new StringBuilder();
        boolean encontrado = false;
        try{
            FileReader fileReader = new FileReader (caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            boolean primeira = true;// quando ha conversao de dados.
            while((linha = bufferedReader.readLine()) != null){
                if(primeira){
                    primeira = false;
                    conteudoAtualizado.append(linha).append("\n");
                    continue;
                }
                
                String[] partes = linha.split(";");
                int taskId = Integer.parseInt(partes[0]);
                int id_lista = Integer.parseInt(partes[1]);
                String Listanome = partes[2];
                String Listatitulo = partes[3];
                String Listatipo = partes[4];
                //String Listaconcluido = partes[5];
                //String DataHora = partes[6];
                if(FiltrarDados(lista, task, Listanome, taskId)){
                    
                    encontrado = true;
                    
                    partes[3] = novo_titulo;
                    partes[4] = novo_tipo;
                    partes[5] = opcao;
                    partes[6] = data;
                    
                    conteudoAtualizado.append(String.join(";", partes)).append("\n");
                    
                }else{
                    conteudoAtualizado.append(linha).append("\n");
                }
                
            }
            
            if (encontrado){
                FileWriter fileWriter = new FileWriter(caminho);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(conteudoAtualizado.toString());
                bufferedWriter.close();
            }
            
            bufferedReader.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    // Salvar dados da lista.
    public void salvarDados(Task task, String lista_nome, int list_id){
        try{
            String caminho = "./dados/tarefas.csv"; // Caminho para o arquivo .csv
            File file = new File(caminho); 
            boolean existe = file.exists();
            FileWriter fileWriter = new FileWriter(caminho ,StandardCharsets.ISO_8859_1, existe);
            
            if(existe == false){
                //fileWriter.write("Id;Lista;Titulo;Tipo;Concluido;Horario_e_data\n");
                fileWriter.write("Id;ListaId;Lista;Titulo;Tipo;Concluido;Horario_e_data\n");
            }
            
            fileWriter.write(task.getId() + ";" + list_id + ";" + lista_nome + ";" + task.getTitulo() + ";" + task.getTipo() + ";" + task.getConcluido() + ";" + task.getHorario_Data() + "\n");
            
            //writer.flush();
            
            fileWriter.close();
            
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public abstract void marcarTask(Task task);
    public abstract String getHorario_Data();
    
}
