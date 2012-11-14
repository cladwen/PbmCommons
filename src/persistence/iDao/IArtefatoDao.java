/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.iDao;

import java.util.SortedMap;
import model.Artefato;
import model.Partida;
import persistence.PersistenceException;

/**
 *
 * @author gurgel
 */
public interface IArtefatoDao {

    public void clear();

    public Artefato get(int id) throws PersistenceException;

    public SortedMap<String, Artefato> list(Partida partida) throws PersistenceException;
}
