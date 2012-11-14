/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.iDao;

import java.util.SortedMap;
import model.Cidade;
import model.Partida;
import persistence.PersistenceException;

/**
 *
 * @author gurgel
 */
public interface ICidadeDao {

    public void clear();

    public Cidade get(int id) throws PersistenceException;

    public SortedMap<String, Cidade> list(Partida partida) throws PersistenceException;
}
