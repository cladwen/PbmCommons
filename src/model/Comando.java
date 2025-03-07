/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import baseLib.BaseModel;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 *
 * @author gurgel
 */
public class Comando implements Serializable {

    public static final String DATEFORMAT = "dd-MM-yyyy hh:mm:ss Z";
    private String jogadorNome;
    private int jogadorId;
    private int serial;
    private String partidaCod;
    private int partidaId;
    private int turno;
    private Date creationTime;
    private final List<ComandoDetail> comandos = new ArrayList();
    private final SortedMap<Integer, String> packages = new TreeMap<>();

    public Comando() {
        setCreationTime();
    }

    public void addComando(BaseModel actor, Ordem ordem, List<String> parametrosId, List<String> parametrosDisplay, Instant timeLastChange) {
        if (ordem == null) {
            return;
        }
        ComandoDetail comDet = new ComandoDetail(actor, ordem, parametrosId, parametrosDisplay, timeLastChange);
        this.comandos.add(comDet);
    }

    public void addPackage(Nacao nacao, List<Habilidade> packagesList) {
        try {
            packages.put(nacao.getId(), getPackagesToDb(packagesList));
        } catch (NullPointerException e) {
            //sem nacao???
        }
    }

    private String getPackagesToDb(List<Habilidade> packagesList) {
        String ret = ";";
        for (Habilidade habilidade : packagesList) {
            if (habilidade.isPackage()) {
                ret += habilidade.getCodigo().substring(1);
            }
        }
        if (ret.equals(";")) {
            return ";-;";
        }
        return ret;
    }

    public SortedMap<Integer, String> getPackages() {
        return this.packages;
    }

    public int size() {
        return this.comandos.size();
    }

    public int getJogadorId() {
        return jogadorId;
    }

    public int getPartidaId() {
        return partidaId;
    }

    public List<ComandoDetail> getOrdens() {
        return this.comandos;
    }

    public String getJogadorNome() {
        return jogadorNome;
    }

    public String getPartidaCod() {
        return partidaCod;
    }

    public int getTurno() {
        return turno;
    }

    public void setInfos(Partida partida) {
        this.partidaCod = partida.getCodigo();
        this.partidaId = partida.getId();
        this.turno = partida.getTurno();
        this.jogadorId = partida.getJogadorAtivo().getId();
        this.jogadorNome = partida.getJogadorAtivo().getNome();
        this.serial = 1234 + partidaId + jogadorId;
    }

    public boolean isSerial() {
        return this.serial == 1234 + this.getJogadorId() + this.getPartidaId();
    }

    private Date getCreationTime() {
        return creationTime;
    }

    public String getCreationTimeStamp() {
        DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/London"));//GMT+13
        return formatter.format(getCreationTime());
    }

    private void setCreationTime() {
        this.creationTime = new Date();
    }
}
