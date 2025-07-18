
package PacoteClasses;
// TAREFA ROTINA (QUE FAZ DIARIAMENTE)
public class TaskRotina extends Task{

    public TaskRotina(int id, String titulo, String tipo, String concluido) {
        super(id, titulo, tipo, concluido);
    }

    @Override
    public void marcarTask(Task task) {
        task.setConcluido("sim");
    }

    @Override
    public String getHorario_Data() {
        return "-";
    }
    
    
}
