package br.edu.utfpr.biblioteca.salas.model.dao;

import br.edu.utfpr.biblioteca.salas.model.entity.UsuarioPO;
import br.edu.utfpr.biblioteca.salas.model.entity.ReservaPO;
import br.edu.utfpr.biblioteca.salas.model.entity.StatusPO;
import br.edu.utfpr.biblioteca.salas.tools.CalendarioHelper;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class ReservaDAO extends GenericDAO<ReservaPO> implements Serializable {

    public ReservaDAO() {
        super(ReservaPO.class);
    }

    /**
     * Insere uma reserva no BD, retorna true se a reserva foi inserida e false
     * se houver algum erro.
     *
     * @param reserva
     * @return Boolean
     */
    @Override
    public boolean insert(ReservaPO reserva) {
        UsuarioDAO usuario = new UsuarioDAO();
        if (usuario.canReservar(reserva.getUsuario(), reserva.getDataInicial())) {

            entityManager.getTransaction().begin();
            if (isReservado(reserva)) {
                entityManager.getTransaction().rollback();
                return false;
            }
            entityManager.persist(reserva);
            entityManager.getTransaction().commit();
            return true;
        }

        return false;
    }

    @Override
    public void delete(ReservaPO reserva) {
        throw new UnsupportedOperationException();
    }

    /**
     * Dado uma data-hora, este método retorna um lista de reservas
     * correspondente a data.
     *
     * @param date
     * @return List<ReservaPO>
     */
    public List<ReservaPO> listByDateTime(Date date) {
        Query q = entityManager.createQuery("SELECT e FROM Reserva e "
                + "WHERE e.dataInicial=:dataInicial AND e.status != :inativa");
        q.setParameter("dataInicial", date);
        q.setParameter("inativa", new StatusPO("inativa"));
        return q.getResultList();
    }

    /**
     * Dado uma data-hora, este método retorna um lista de reservas
     * correspondente a data e o id da sala.
     *
     * @param date
     * @param idSala
     * @return List<ReservaPO>
     */
    public List<ReservaPO> listByDateTimeAndSala(Date date, int idSala) {
        Query q = entityManager.createQuery("SELECT e FROM Reserva e WHERE e.dataIniciaxdataInicial AND e.id =:idSala");
        q.setParameter("dataInicial", date);
        q.setParameter("idSala", idSala);
        return q.getResultList();
    }

    /**
     * Dado uma data-hora e uma sala, este método retorna se existe uma reserva
     * correspondente a data e em outro id da sala.
     *
     * @param date
     * @param idSala
     * @param usuario
     * @return
     */
    public boolean haveReservaByDateIdsalaUsuario(Date date, int idSala, UsuarioPO usuario) {
        Query q = entityManager.createQuery("SELECT COUNT(e) FROM Reserva e WHERE e.dataInicial =:dataInicial AND "
                + "e.id !=:idSala AND e.status != :status AND e.usuario = :usuario");
        q.setParameter("dataInicial", date);
        q.setParameter("idSala", idSala);
        q.setParameter("status", new StatusPO("inativa"));
        q.setParameter("usuario", usuario);
        Long qtdReservas = (Long) q.getSingleResult();

        return qtdReservas != 0;
    }

    /**
     * SELECT busca a quantidade de reservas exitentes dado dia, mes, ano, e
     * hora inicial
     *
     * @param date
     * @return int
     */
    public int getQuantidadeReservas(Date date) {
        String strData = CalendarioHelper.getDataToDataBase(date);
        Query q = entityManager.createNativeQuery("SELECT count(*) FROM Reservas r WHERE r.data_inicial = '" + strData + "'");
        long qtdeReservas = 0;
        try {
            qtdeReservas = (long) q.getSingleResult();
        } catch (Exception ex) {
            return 0;
        }
        return (int) qtdeReservas;
    }

    /**
     * Busca por uma reserva ativa na data-hora e sala contidas no objeto dado.
     * Retorna true se já existe uma reserva neste horario ou false se não foi
     * encontrada nenhuma.
     *
     * @param reserva
     * @return
     */
    public boolean isReservado(ReservaPO reserva) {
        try {
            Query q = entityManager.createQuery("SELECT e FROM Reserva e WHERE e.dataInicial = :dataInicial AND e.sala = :sala AND e.status != :status");
            q.setParameter("dataInicial", reserva.getDataInicial());
            q.setParameter("sala", reserva.getSala());
            q.setParameter("status", new StatusPO("inativa"));
            q.getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    /**
     * SELECT busca as reservas entre duas datas, data inicial e data final.
     * A data inicial deve possui hora igual a 8
     * A data final deve possui hora igual a 22
     * @param dataInicial
     * @param dataFinal
     * @return lista de reservas
     */
    public List<ReservaPO> getReservasPeriodo(Date dataInicial, Date dataFinal) {
        Query q = entityManager.createQuery("SELECT e FROM Reserva e WHERE e.dataInicial >= :dataInicial AND e.dataInicial <= :dataFinal");
        q.setParameter("dataInicial", dataInicial);
        q.setParameter("dataFinal", dataFinal);
        return q.getResultList();
    }
}
