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

		try { // transiente - objeto novo: ainda n�o persistente
				// persistente - apos ser persistido
				// destacado - objeto persistente n�o vinculado a um entity manager
				// ==>

			em = FabricaDeEntityManager.criarSessao();

			// Cria uma transacao com o SGBD
			tx = em.getTransaction();
			tx.begin();

			// ----------------------------------------------------------------------------------------------------------------------
			// 1 - � executado um SQL insert no banco de dados, Mas o commit nao foi feito
			// Logo, para um outro usuario do SGBD, a opera��o de insert nao foi feita.
			// 2 - � Atribuido um valor Id para "umOrgao" baseado no auto incremento no
			// banco de dados
			// 3 - O objeto eh monitorado pelo entity manager. Caso ele seja atualizado, �
			// imediata
			// mente feito um SQL update.
			em.persist(umOrgao);

			tx.commit();

			return umOrgao.getNumero();
		}

		// todos os erros lan�ados pela JPA s�o run time exceptions.
		catch (RuntimeException e) {
			// Foi criada uma transa��o com o SGBD
			if (tx != null) {
				System.out.println("Falha na insercao, fazendo rollback");
				try {
					tx.rollback();
					System.out.println("rollback feito com sucesso");
				}

				// O abre e fecha chaves serve para matar a exce��o em caso de falha no
				// rollback. Ou seja: a exce��o he � ignorada, por n�o ser importante
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
				throw new OrgaoNaoEncontradoException("Orgao n�o encontrado");
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
				throw new OrgaoNaoEncontradoException("Orgao n�o encontrado");
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

			// Caracter�sticas no m�todo find():
			// 1. � gen�rico: n�o requer um cast.
			// 2. Retorna null caso a linha n�o seja encontrada no banco.

			if (umOrgao == null) {
				throw new OrgaoNaoEncontradoException("Orgao n�o encontrado");
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

			// esse comando N�O � SQL. Por isso que parece estranho. Se fosse o
			// comando seria algo do tipo:
			//
			// "SELECT * FROM Orgao ORDER BY Orgao"
			//
			// o comando dado pela query string � proprio da framework
			// JPAQL.
			String queryString = "select p from Orgao p order by p.id";

			// @SuppressWarnings serve para o compilador n�o encher o saco com
			// o fato de que o query na linha 187 n�o garantir o retorno de um arrayList
			@SuppressWarnings("unchecked")
			List<Orgao> prods = em.createQuery(queryString).getResultList();

			return prods;
		} finally {
			em.close();
		}
	}
}
