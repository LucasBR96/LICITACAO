package exercicio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

import dao.FabricaDeEntityManager;
import dao.OrgaoDAO;
import execao.EstadoDeObjetoObsoletoException;
import execao.OrgaoNaoEncontradoException;
import modelos.Orgao;

public class JPAOrgaoDao implements OrgaoDAO {
	public long inclui(Orgao umOrgao)  {
		EntityManager em = null;
		EntityTransaction tx = null;

		try { // transiente - objeto novo: ainda nï¿½o persistente
				// persistente - apos ser persistido
				// destacado - objeto persistente nï¿½o vinculado a um entity manager
				// ==>

			em = FabricaDeEntityManager.criarSessao();

			// Cria uma transacao com o SGBD
			tx = em.getTransaction();
			tx.begin();

			// ----------------------------------------------------------------------------------------------------------------------
			// 1 - é executado um SQL insert no banco de dados, Mas o commit nao foi feito
			// Logo, para um outro usuario do SGBD, a operação de insert nao foi feita.
			// 2 - ï¿½ Atribuido um valor Id para "umOrgao" baseado no auto incremento no
			// banco de dados
			// 3 - O objeto eh monitorado pelo entity manager. Caso ele seja atualizado, ï¿½
			// imediata
			// mente feito um SQL update.
			em.persist(umOrgao);

			tx.commit();

			return umOrgao.getNumero();
		}

		// todos os erros lanï¿½ados pela JPA sï¿½o run time exceptions.
		catch (RuntimeException e) {
			// Foi criada uma transaï¿½ï¿½o com o SGBD
			if (tx != null) {
				System.out.println("Falha na insercao, fazendo rollback");
				try {
					tx.rollback();
					System.out.println("rollback feito com sucesso");
				}

				// O abre e fecha chaves serve para matar a exceï¿½ï¿½o em caso de falha no
				// rollback. Ou seja: a exceï¿½ï¿½o he ï¿½ ignorada, por nï¿½o ser importante
				catch (RuntimeException he) {
					System.out.println("Falha no rollback");
				}
			}
			throw e;
		} finally {
			// Destruir o entity manager
			em.close();
		}
	}

	public void altera(Orgao umOrgao) throws OrgaoNaoEncontradoException, EstadoDeObjetoObsoletoException {
		EntityManager em = null;
		EntityTransaction tx = null;
		Orgao Orgao = null;
		try {
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();

			// Impede que qualquer outro computador leia o produto.
			Orgao = em.find(Orgao.class, new Long(umOrgao.getNumero() ), LockModeType.PESSIMISTIC_WRITE);
			
			if(Orgao == null)
			{	tx.rollback(); //encerrando transacao
				throw new OrgaoNaoEncontradoException("Orgao não encontrado");
			}
			
			em.merge(umOrgao);

			tx.commit();
		} catch( OptimisticLockException opt) {
			if( tx != null ) tx.rollback();
			throw new EstadoDeObjetoObsoletoException( "A Operacao nao foi efetuada. Os dados que voce tentou salvar foram modificados por outro usuario" ) ;
			
		} catch (RuntimeException e) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (RuntimeException he) {
				}
			}
			throw e;
		}finally {
			em.close();
		}
	}

	public void exclui(long numero) throws OrgaoNaoEncontradoException 
	{	EntityManager em = null;
		EntityTransaction tx = null;
		
		try
		{	
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();
 
			// Impede que qualquer outro computador leia o produto.
			Orgao umOrgao = em.find(Orgao.class, new Long(numero) );
			
			if(umOrgao == null)
			{	tx.rollback(); //encerrando transacao
				throw new OrgaoNaoEncontradoException("Orgao não encontrado");
			}
			
			em.remove(umOrgao);
			tx.commit();
		} 
		catch(RuntimeException e)
		{   
			if (tx != null)
		    {   
				try
		        {	tx.rollback();
		        }
		        catch(RuntimeException he)
		        { }
		    }
		    throw e;
		}
		finally
		{   em.close();
		}
	}

	public Orgao recuperaUmOrgao(long numero) throws OrgaoNaoEncontradoException {
		EntityManager em = null;

		try {
			em = FabricaDeEntityManager.criarSessao();

			Orgao umOrgao = em.find(Orgao.class, numero);

			// Caracterï¿½sticas no mï¿½todo find():
			// 1. ï¿½ genï¿½rico: nï¿½o requer um cast.
			// 2. Retorna null caso a linha nï¿½o seja encontrada no banco.

			if (umOrgao == null) {
				throw new OrgaoNaoEncontradoException("Orgao não encontrado");
			}
			return umOrgao;
		} finally {
			em.close();
		}
	}

	public List<Orgao> recuperaOrgaos() {
		EntityManager em = null;

		try {
			em = FabricaDeEntityManager.criarSessao();

			// esse comando Nï¿½O ï¿½ SQL. Por isso que parece estranho. Se fosse o
			// comando seria algo do tipo:
			//
			// "SELECT * FROM Orgao ORDER BY Orgao"
			//
			// o comando dado pela query string ï¿½ proprio da framework
			// JPAQL.
			String queryString = "select p from Orgao p order by p.id";

			// @SuppressWarnings serve para o compilador nï¿½o encher o saco com
			// o fato de que o query na linha 187 nï¿½o garantir o retorno de um arrayList
			@SuppressWarnings("unchecked")
			List<Orgao> prods = em.createQuery(queryString).getResultList();

			return prods;
		} finally {
			em.close();
		}
	}
}
