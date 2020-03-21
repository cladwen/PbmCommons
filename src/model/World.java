/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author gurgel
 */
public class World implements Serializable {

    private Partida partida;
    private VictoryPointsGame victoryPoints;
    private final SortedMap<String, Jogador> jogadores = new TreeMap();
    private SortedMap<String, Nacao> nacoes = new TreeMap();
    private SortedMap<String, Personagem> personagens = new TreeMap();
    private SortedMap<String, Cidade> cidades = new TreeMap();
    private SortedMap<String, Exercito> exercitos = new TreeMap();
    //PENDING: mover atributos de cenario que estao em World.
    private SortedMap<String, Local> locais = new TreeMap();
    private SortedMap<String, Artefato> artefatos = new TreeMap();
    private final SortedMap<String, Habilidade> packages = new TreeMap();

    public SortedMap<String, Exercito> getExercitos() {
        return this.exercitos;
    }

    public Local getLocal(String coord) {
        try {
            return this.locais.get(coord);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public SortedMap<String, Nacao> getNacoes() {
        return nacoes;
    }

    public void setNacoes(SortedMap<String, Nacao> nacoes) {
        this.nacoes = nacoes;
        for (Nacao nacao : nacoes.values()) {
            addJogador(nacao.getOwner());
        }
    }

    private void addJogador(Jogador jogador) {
        try {
            this.jogadores.put(jogador.getLogin(), jogador);
        } catch (NullPointerException ex) {
        }
    }

    public SortedMap<String, Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(SortedMap<String, Cidade> cidades) {
        this.cidades = cidades;
    }

    public void addCidade(Cidade cidade) {
        this.cidades.put(cidade.getCodigo(), cidade);
    }

    public void addLocal(Local local) {
        this.locais.put(local.getCodigo(), local);
    }

    public SortedMap<String, Local> getLocais() {
        return locais;
    }

    public void setLocais(SortedMap<String, Local> locais) {
        this.locais = locais;
    }

    public SortedMap<String, Artefato> getArtefatos() {
        return artefatos;
    }

    public void setArtefatos(SortedMap<String, Artefato> artefatos) {
        this.artefatos = artefatos;
    }

    public void setPersonagens(SortedMap<String, Personagem> personagens) {
        this.personagens = personagens;
    }

    public void setExercito(SortedMap<String, Exercito> exercitos) {
        this.exercitos = exercitos;
    }

    public void addExercito(SortedMap<String, Exercito> exercitos) {
        for (Exercito exercito : exercitos.values()) {
            if (!this.exercitos.containsKey(exercito.getCodigo())) {
                this.exercitos.put(exercito.getCodigo(), exercito);
            }
        }
    }

    public SortedMap<String, Personagem> getPersonagens() {
        return personagens;
    }

    public SortedMap<String, Habilidade> getPackages() {
        return packages;
    }

    public void setPackages(SortedMap<String, Habilidade> list, Cenario cenario) {
        this.packages.clear();
        for (Habilidade habilidade : list.values()) {
            for (String cdHab : habilidade.getHabilidades().keySet()) {
                if (cenario.hasHabilidade(cdHab)) {
                    this.packages.put(habilidade.getCodigo(), habilidade);
                    break;
                }
            }
        }
    }

    public VictoryPointsGame getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(VictoryPointsGame victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
