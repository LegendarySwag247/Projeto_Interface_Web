
package PacoteClasses;
import Telas.Inicial;
import Telas.Erro;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Lista {
    
    private int id;
    private String nome;
    private static final List<Lista> listas = new ArrayList<>();
    private static final List<Task> tasks = new ArrayList<>();
    private static final List<Usuario> users = new ArrayList<>();
    
    public Lista(int id,String nome){
        this.id = id;
        this.nome = nome;
    }
    
    public int getId(){
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public static int pegarIdTarefa(String titulo, String tipo, String concluido, String data_horario){
        List<Task> tasks = Lista.ListarTask();
        boolean achou = false;
        int identidade = 0;
        for(Task task : tasks){
            if(task.getTitulo().equals(titulo) && task.getTipo().equals(tipo) && task.getConcluido().equals(concluido) && task.getHorario_Data().equals(data_horario) ){
                achou = true;
                identidade = task.getId();
                break;
            }
        }
        
        if(achou){
            return identidade;
        }else{
            return -1;
        }
    
    }
        
    // Filtro para editar
    public boolean FiltroEditar(Task task, int id, String titulo, String tipo){
        return (task.getTitulo().equals(titulo) && task.getTipo().equals(tipo) && task.getId() == id);
    }
    
    //funcionalidades do programa
    public void editar( String velhoTitulo, String velhoTipo, String novoTitulo, String novoTipo, String concluido){
        for(Task task : tasks){
            if(task.getTitulo().equals(velhoTitulo) && task.getTipo().equals(velhoTipo)){
                task.setTitulo(novoTitulo);
                task.setTipo(novoTipo);
                task.setConcluido(concluido);
                task.marcarTask(task);
            }
        }
    }
    // Listar todos os usuarios
    public static List<Usuario> ListarUser(){
        users.clear();
        String caminho = "./dados/usuario.csv"; // Caminho para o arquivo .csv
        try{
            FileReader fileReader = new FileReader (caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            boolean primeira = true;// quando ha conversao de dados.
            while((linha = bufferedReader.readLine()) != null){
                if(primeira){
                    primeira = false;
                    continue;
                }
                
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String email = partes[2];
                String telefone = partes[3];
                String senha = partes[4];
                
                Usuario user = new Usuario(id, nome, email, telefone, senha);
                Lista.adicionarUsers(user);
                
            }
       
            
            bufferedReader.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return users;
    }
    // Listar as Listas das Tarefas
    public static List<Lista> ListarLista(){
        listas.clear();
        String caminho = "./dados/tarefas.csv"; // Caminho para o arquivo .csv
        try{
            FileReader fileReader = new FileReader (caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            boolean primeira = true;
            while((linha = bufferedReader.readLine()) != null){
                if(primeira){
                    primeira = false;
                    continue;
                }
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[1]);// id da lista
                String nome = partes[2]; // nome da lista.
                //String titulo = partes[2];
                
                boolean jaExiste = false;
                
                Lista l = new Lista(id, nome);
                
                for (Lista l_lista : listas){
                    if (l_lista.getId() == id && l_lista.getNome().equalsIgnoreCase(nome)){
                        jaExiste = true;
                        break;
                    }
                }
                
                if(jaExiste == false){
                    Lista.adicionarLista(l);
                }
                
            }
            bufferedReader.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return listas;
    }
    
    // Listar as Tarefas
    public static List<Task> ListarTask(){
        tasks.clear();
        String caminho = "./dados/tarefas.csv"; // Caminho para o arquivo .csv
        try{
            FileReader fileReader = new FileReader (caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            boolean primeira = true;
            while((linha = bufferedReader.readLine()) != null){
                if(primeira){
                    primeira = false;
                    continue;
                }
                String[] partes = linha.split(";");
                int taskId = Integer.parseInt(partes[0]);// tarefa id
                int id = Integer.parseInt(partes[1]);// id da lista
                String nome = partes[2]; // nome da lista.
                String titulo = partes[3];
                String tipo = partes[4];
                String concluido = partes[5];
                String horario_data = partes[6];
                
                
                if(tipo.equals("COMPROMISSO")){
                    Task task_c = new TaskCompromisso(taskId ,titulo, tipo, concluido, horario_data);
                    Lista.adicionarTarefa(task_c);
                }else{
                    Task task_r = new TaskRotina(taskId ,titulo, tipo, concluido);
                    Lista.adicionarTarefa(task_r);
                }
                
            }
            bufferedReader.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return tasks;
    }
    // Carregar todos os dados da Tarefa
    public static List<Task> CarregarTask(int lista_id, String lista_nome){
        String caminho = "./dados/tarefas.csv"; // Caminho para o arquivo .csv
        try{
            FileReader fileReader = new FileReader (caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            boolean primeira = true;
            while((linha = bufferedReader.readLine()) != null){
                if(primeira){
                    primeira = false;
                    continue;
                }
                String[] partes = linha.split(";");
                //int taskId = Integer.parseInt(partes[0]);// id fa tarefa
                int id = Integer.parseInt(partes[1]);// id da lista
                String nome = partes[2]; // nome da lista.
                String titulo = partes[3];
                String tipo = partes[4];
                String concluido = partes[5];
                String horario_data = partes[6];
                
                if(id == lista_id && nome.equals(lista_nome)){
                    Inicial.adicionarRow(new Object[]{
                        titulo,
                        tipo,
                        concluido,
                        horario_data
                    });
                }
                
            }
            bufferedReader.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return tasks;
    }
    
    // Buscar usuario qualquer.
    public static List<Usuario> BuscarUser(String e_mail, String password){
        String caminho = "./dados/usuario.csv"; // Caminho para o arquivo .csv
        String name = " ";
        int user_id = 0;
        boolean acesso = false;
        try{
            FileReader fileReader = new FileReader (caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            boolean primeira = true;// quando ha conversao de dados.
            while((linha = bufferedReader.readLine()) != null){
                if(primeira){
                    primeira = false;
                    continue;
                }
                
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String email = partes[2];
                String telefone = partes[3];
                String senha = partes[4];
                
                if(email.equals(e_mail) && senha.equals(password)){
                    acesso = true;
                    name = nome;
                    user_id = id;
                    break;
                }else{
                    acesso = false;
                }
                
                
            }
            
            if(acesso){
                Inicial inicial = new Inicial();
                inicial.setVisible(true);
                inicial.colocarNome(name);
                inicial.colocarUserId(user_id);
                inicial.CarregarDadosDaLista();
                //inicial.CarregarDadosDaTarefa();
            }else{
                Erro erro = new Erro();
                erro.setVisible(true);
            }
            
            bufferedReader.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return users;
    }
    
    public static void adicionarTarefa(Task task){
        tasks.add(task);
    }
    public static void adicionarUsers(Usuario user){
        users.add(user);
    }
    
    public static void adicionarLista(Lista lista){
        listas.add(lista);
    }
    
    // Acrecentar Id no arquivo .csv
    public static int adicionarId(String tipo){
        String caminho;
        if(tipo.equals("user")){
            caminho = "./dados/usuario.csv"; // Caminho para o arquivo .csv
        }else{
            caminho = "./dados/tarefas.csv"; // Caminho para o arquivo .csv
        }
        int maiorVal = -1;
        try{
            FileReader fileReader = new FileReader (caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            boolean primeira = true;
            while((linha = bufferedReader.readLine()) != null){
                if(primeira){
                    primeira = false;
                    continue;
                }
                
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                
                if(id > maiorVal){
                    maiorVal = id;
                }
                
            }
            
            bufferedReader.close();
            
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return maiorVal + 1;
    }
    
}
