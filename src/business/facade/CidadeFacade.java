/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.facade;

import baseLib.SysApoio;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.*;
import msgs.Msgs;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import persistence.BundleManager;
import persistence.SettingsManager;

/**
 *
 * @author gurgel
 */
public class CidadeFacade implements Serializable {

    private static final Log log = LogFactory.getLog(CidadeFacade.class);
    private static final BundleManager labels = SettingsManager.getInstance().getBundleManager();
    private static final NacaoFacade nacaoFacade = new NacaoFacade();
    private static final LocalFacade localFacade = new LocalFacade();

    public int getArrecadacaoImpostos(Cidade cidade) {
        return cidade.getArrecadacaoImpostos();
    }

    public String getCoordenadas(Cidade cidade) {
        return localFacade.getCoordenadas(cidade.getLocal());
    }

    public int getDocas(Cidade cidade) {
        return cidade.getDocas();
    }

    public String getDocasNome(Cidade cidade) {
        return Msgs.cidadeDocas[cidade.getDocas()];
    }

    public Jogador getJogador(Cidade cidade) {
        Jogador jogador = null;
        try {
            jogador = cidade.getNacao().getOwner();
        } catch (NullPointerException ex) {
            //faz nada, so retorna null
        }
        return jogador;
    }

    public int getLealdade(Cidade cidade) {
        return cidade.getLealdade();
    }

    public int getLealdadeDelta(Cidade cidade) {
        return (cidade.getLealdade() - cidade.getLealdadeAnterior());
    }

    public Local getLocal(Cidade cidade) {
        return cidade.getLocal();
    }

    public String getNacaoNome(Cidade cidade) {
        return nacaoFacade.getNome(cidade.getNacao());
    }

    public String getNome(Cidade cidade) {
        return cidade.getNome();
    }

    public int getFortificacao(Cidade cidade) {
        return cidade.getFortificacao();
    }

    public String getFortificacaoNome(Cidade cidade) {
        return Msgs.cidadeFortificacao[cidade.getFortificacao()];
    }

    public int getTamanho(Cidade cidade) {
        return cidade.getTamanho();
    }

    public String getTamanhoNome(Cidade cidade) {
        return Msgs.cidadeTamanho[cidade.getTamanho()];
    }

    public String getTamanhoFortificacao(Cidade cidade) {
        String ret = Msgs.cidadeTamanho[cidade.getTamanho()];
        if (cidade.isFortificado()) {
            ret += "/" + Msgs.cidadeFortificacao[cidade.getFortificacao()];
        }
        return ret;
    }

    public boolean isCapital(Cidade cidade) {
        return cidade.isCapital();
    }

    public boolean isFortificacao(Cidade cidade) {
        return cidade != null && cidade.getFortificacao() > 0;
    }

    public boolean isOculto(Cidade cidade) {
        return cidade.isOculto();
    }

    public boolean isSitiado(Cidade cidade) {
        return cidade.isSitiado();
    }

    public List listaPresencas(Cidade cidade) {
        PersonagemFacade personagemFacade = new PersonagemFacade();
        List listaPresencas = new ArrayList();
        //lista personagems
        try {
            Iterator lista = cidade.getLocal().getPersonagens().values().iterator();
            while (lista.hasNext()) {
                Personagem personagem = (Personagem) lista.next();
                if (personagemFacade.isComandaExercito(personagem)) {
                    listaPresencas.add(personagem.getExercito());
                } else {
                    listaPresencas.add(personagem);
                }
            }
        } catch (NullPointerException ex) {
        }
        //FIXME: lista exercitos
        //FIXME: lista artefatos
        return listaPresencas;
    }

    public Color getNacaoColor(Cidade cidade) {
        int id;
//        int[][] cor = {
//            {180, 180, 180},
//            {255, 0, 0},
//            {0, 0, 255},
//            {255, 255, 0},
//            {0, 255, 0}};
        /*
         *
         * @DB.NACAO.NOME.ESPARTA# red @DB.NACAO.NOME.ATENAS# blue
         * @DB.NACAO.NOME.MACEDONIA# yellow @DB.NACAO.NOME.PERSIA# green
         * @DB.NACAO.NOME.TRACIA# @DB.NACAO.NOME.MILLETUS#
         * @DB.NACAO.NOME.ILLYRIA# @DB.NACAO.NOME.EPIRUS#
         */
        int[][] cor = {
            {180, 180, 180},
            {255, 0, 0}, //red A
            {0, 0, 255}, //azul B
            {255, 255, 0}, //amarelo A
            {0, 128, 0}, //verde B
            {0, 255, 255}, //azul claro B
            {255, 110, 110}, //pink A
            {255, 128, 0}, //laranja
            {128, 255, 0}, //verde amarelado
            {185, 185, 0}, //verde/amarelo A
            {0, 255, 128}, //verde claro B
            {255, 255, 255}, //branco
            {128, 0, 255}, //roxo
            {0, 128, 255}, //azul medio
            {0, 0, 0} //preto
        };
        try {
            id = SysApoio.parseInt(cidade.getNacao().getCodigo());
        } catch (NullPointerException ex) {
            //nacao desconhecida
            id = 0;
        }
        try {
            return (new Color(cor[id][0], cor[id][1], cor[id][2]));
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            return (new Color(cor[0][0], cor[0][1], cor[0][2]));
        }
    }

    public String getOculto(Cidade cidade) {
        return SysApoio.iif(cidade.isOculto(), labels.getString("SIM"), labels.getString("NAO"));
    }

    public String getCapital(Cidade cidade) {
        return SysApoio.iif(cidade.isCapital(), labels.getString("SIM"), labels.getString("NAO"));
    }

    public String getSitiado(Cidade cidade) {
        return SysApoio.iif(cidade.isSitiado(), labels.getString("SIM"), labels.getString("NAO"));
    }

    public Nacao getNacao(Cidade cidade) {
        return cidade.getNacao();
    }

    public String getNomeCoordenada(Cidade cidade) {
        return cidade.getComboDisplay();
    }

    public String getRacaNome(Cidade cidade) {
        try {
            return cidade.getRaca().getNome();
        } catch (NullPointerException e) {
            try {
                return cidade.getNacao().getRaca().getNome();
            } catch (NullPointerException ex) {
                return labels.getString("NENHUM");
            }
        }
    }

    public Raca getRaca(Cidade cidade) {
        try {
            return cidade.getRaca();
        } catch (NullPointerException e) {
            try {
                return cidade.getNacao().getRaca();
            } catch (NullPointerException ex) {
                return null;
            }
        }
    }

    public int getProducao(Cidade cidade, Produto produto) {
        final int producao = cidade.getProducao(produto);
        try {
            if (!produto.isMoney() || producao >= 250) {
                return producao;
            } else if (cidade.getNacao().getHabilidadesNacao().containsKey("0039") && cidade.getNacao().getRaca() == cidade.getRaca()) {
                //se mesma cultura e com habilidade, entao garante minimo de 250
                return 250;
            } else {
                return producao;
            }
        } catch (NullPointerException ex) {
            return producao;
        }
    }

    public int getEstoque(Cidade cidade, Produto produto) {
        return cidade.getEstoque(produto);
    }

    public int getDefesa(Cidade cidade) {
        final int[] bonusFortificacaoCumulativo = {0, 2000, 6000, 10000, 16000, 24000};
        final int[] bonusTamanho = {0, 200, 500, 1000, 2500, 5000};
        int ret = 0;
        ret += bonusTamanho[cidade.getTamanho()] + bonusFortificacaoCumulativo[cidade.getFortificacao()];
        if (cidade.getLealdade() == 0) {
            ret += ret;
        } else {
            ret += ret * cidade.getLealdade() / 100;
        }
        return ret;
    }
}
