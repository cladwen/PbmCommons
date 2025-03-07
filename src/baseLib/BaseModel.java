/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package baseLib;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;
import model.Habilidade;
import model.Nacao;
import model.PersonagemOrdem;
import persistenceCommons.SysApoio;

/**
 *
 * @author gurgel
 */
public class BaseModel implements Serializable, IBaseModel, Comparable<Object> {

    private int id;
    private String nome;
    private String codigo;
    private boolean changed = false;
    private String resultados = "";
    private SortedMap<String, Habilidade> habilidades = new TreeMap<>();
    private final SortedMap<Integer, PersonagemOrdem> acao = new TreeMap();
    private final SortedMap<Integer, PersonagemOrdem> acaoExecutadas = new TreeMap();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getComboId() {
        return this.codigo;
    }

    @Override
    public String getComboDisplay() {
        return this.nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final void setNome(String nome) {
        this.nome = nome;
    }

    public final void setCodigo(String codigo) {
        this.codigo = SysApoio.removeAcentos(codigo);
    }

    public String getHabilidadesToDb() {
        String ret = ";";
        for (String cdHab : getHabilidades().keySet()) {
            if (cdHab.equals(";-;")) {
                //skip none, it will be added later. Serves to purge NONE when another Hab is added
                continue;
            }
            ret += cdHab.substring(1);
        }
        if (ret.equals(";")) {
            return ";-;";
        }
        return ret;
    }

    public int getHabilidadesPoints() {
        int ret = 0;
        for (Habilidade habilidade : getHabilidades().values()) {
            ret += habilidade.getCost();
        }
        return ret;
    }

    /**
     * @return the habilidades
     */
    public SortedMap<String, Habilidade> getHabilidades() {
        return habilidades;
    }

    /**
     * @param habilidades the habilidades to set
     */
    public void setHabilidades(SortedMap<String, Habilidade> habilidades) {
        this.habilidades = habilidades;
    }

    public void addHabilidades(SortedMap<String, Habilidade> habilidades) {
        this.habilidades.putAll(habilidades);
    }

    public void addHabilidade(Habilidade habilidade) {
        remHabilidadeNone();
        this.habilidades.put(habilidade.getCodigo(), habilidade);
    }

    public void remHabilidade(Habilidade habilidade) {
        remHabilidade(habilidade.getCodigo());
    }

    public void remHabilidadeNone() {
        this.habilidades.remove(";-;");
    }

    public void remHabilidade(String cdHab) {
        this.habilidades.remove(cdHab);
    }

    public boolean hasHabilidade(String cdHabilidade) {
        return this.habilidades.get(cdHabilidade) != null;
    }

    public int getHabilidadeValor(String cdHabilidade) {
        try {
            return this.habilidades.get(cdHabilidade).getValor();
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public boolean hasHabilidades() {
        return this.habilidades.size() > 0;
    }

    @Override
    public String toString() {
        return this.getNome() + "-" + getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    @Override
    public int compareTo(Object o) {
        BaseModel outro = (BaseModel) o;
        return (this.getCodigo().compareToIgnoreCase(outro.getCodigo()));
    }

    /**
     * @return the changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * @param changed the changed to set
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public boolean isPersonagem() {
        return false;
    }

    public boolean isNacaoClass() {
        return getTpActor().equals("N");
    }

    public boolean isCidadeClass() {
        return getTpActor().equals("C");
    }

    public boolean isExercitoClass() {
        return getTpActor().equals("E");
    }

    //client
    public PersonagemOrdem getAcao(int index) {
        try {
            return this.acao.get(index);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public void setAcao(int index, PersonagemOrdem pOrdem) {
        if (pOrdem != null) {
            this.acao.put(index, pOrdem);
        } else {
            this.acao.remove(index);
        }
    }

    public int getAcaoSize() {
        return this.acao.size();
    }

    public SortedMap<Integer, PersonagemOrdem> getAcoes() {
        return this.acao;
    }

    public void remAcoes() {
        this.acao.clear();
    }

    //server
    public void addAcaoExecutada(PersonagemOrdem po) {
        this.acaoExecutadas.put(this.acaoExecutadas.size(), po);
    }

    public SortedMap<Integer, PersonagemOrdem> getAcaoExecutadas() {
        return this.acaoExecutadas;
    }

    public int getOrdensQt() {
        return 0;
    }

    public Nacao getNacao() {
        return null;
    }

    public String getTpActor() {
        return "-";
    }
}
