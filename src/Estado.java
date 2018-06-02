import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Estado {
    public String estado;
    public HashMap<String, String[]> quadruplas;

    public Estado(String estado) {
        this.estado = estado;
        quadruplas = new HashMap<>();
    }

    public boolean addAction(String nextChar, String action, String nextState){
        if (quadruplas.get(nextChar) == null){
            quadruplas.put(nextChar, new String[]{action, nextState});
            return true;

        } else {
            return false;
        }
    }

    public String[] getAction(String nextChar){
        return quadruplas.get(nextChar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estado estado1 = (Estado) o;
        return Objects.equals(estado, estado1.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estado);
    }
}
