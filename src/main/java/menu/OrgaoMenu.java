package menu;

import java.util.*;

import dao.FabricaDeDAOs;
import dao.OrgaoDAO;
import execao.OrgaoNaoEncontradoException;
import modelos.Orgao;

public class OrgaoMenu {
	
	public static Scanner sc;
	public static OrgaoDAO OrgaoDao;
	public static Orgao orgaoAlvo;
	
	public static void inserirOrgao() {
		
		try {
			orgaoAlvo = new Orgao();
			sc.nextLine();
			
			System.out.printf("Qual é o nome da base: " );
			String nome = sc.nextLine().trim();
			orgaoAlvo.setNome( nome );
			
			System.out.println( "Qual o cep: " );
			String cep = sc.nextLine().trim();
			
			System.out.println( "Qual o endereco:");
			String endereco = sc.nextLine().trim();
			
			orgaoAlvo.setCep(  cep );
			orgaoAlvo.setEndereco(  endereco );
			
			OrgaoDao.inclui( orgaoAlvo );
			System.out.printf( "%nOrgao de numero %d inserido com sucesso", orgaoAlvo.getNumero()  );
			System.out.println( "" );
			
			//sc.nextLine();
			
		}catch( IllegalArgumentException e ) {
			System.out.println( e.getMessage() );
		}
	}

	public static void excluirOrgao() {
		
		try {
			
			System.out.printf("%nDe o numero da base:" );
			long numero = sc.nextLong();
			orgaoAlvo = OrgaoDao.recuperaUmOrgao(numero);
			System.out.println( orgaoAlvo );
			
			System.out.printf( '\n' + "Tem Certeza?"  );
			System.out.println( '\n' + "digite Y ou y para sim e qualquer outra coisa para não");
			String resp = sc.next().toLowerCase();
			if( resp.equals("y") ) {
				OrgaoDao.exclui( numero );
				System.out.printf( "%Orgao excluido%n"  );
			}
			
		}catch( IllegalArgumentException e ) {
			System.out.println( e.getMessage() );
		}catch( OrgaoNaoEncontradoException e ) {
			System.out.println( e.getMessage() );
		}
	}
	
	public static void listarOrgao() {
		
		try {
			
			List <Orgao> orgaos = OrgaoDao.recuperaOrgaos();
			for( Orgao orgao : orgaos ) {
				System.out.println( orgao );
				System.out.println( "");
			}
			
			System.out.printf( "%d Orgaos Listados", orgaos.size()  );
			System.out.println( " " );
			
		}catch( Exception e ) {}
	}
	
	public static void alterarOrgao() {
		
		try {
			System.out.printf("%nDigite o numero da Orgao que deseja alterar: ");
			long numero = sc.nextLong();
			orgaoAlvo = OrgaoDao.recuperaUmOrgao(numero);
			System.out.println( orgaoAlvo );
			
			boolean cond = true;
			int m;
			String s;
			while( cond ) {
				
				System.out.println( "\n" + "O que deseja alterar?" + "\n" + "1 - Nome" + "\n" + "2 - Endereço" + "\n");
				try {
					m = sc.nextInt();
				}catch(  InputMismatchException e ) {
					m = -1;
				}
				
				switch( m ) {
				
				case 1:
					System.out.println( "Digite um novo nome: ");
					
					s = sc.nextLine().trim();
					if( s.length() == 0 ) {
						s = sc.nextLine().trim();
					}
					
					orgaoAlvo.setNome( s );
					OrgaoDao.altera(orgaoAlvo);
					cond = false;
					break;
					
				case 2:
					System.out.println( "Digite um novo endereco: ");
					
					s = sc.nextLine().trim();
					if( s.length() == 0 ) {
						s = sc.nextLine().trim();
					}
					
					orgaoAlvo.setEndereco( s );
					OrgaoDao.altera(orgaoAlvo);
					cond = false;
					break;
					
				default:
					System.out.println( "Escolha invalida");
					break;
					
				}
			}
			
		}catch( Exception e ) {
			System.out.println( e.getMessage() );
		}
	}
	
	public static void begin() {
		
		System.out.println("");
		
		sc = new Scanner( System.in );
		int opt;
		
		// ----------------------------------------------------------------------------------------------------------------------------------------
		// ProdutoDAO.class aponta para um objeto em memoria do tipo class que representa a interface
		// ProdutoDAO. Se a classe é uma planta baixa de um objeto, o objeto class é a planta baixa da classe
		OrgaoDao = FabricaDeDAOs.getDAO( OrgaoDAO.class);
		
		boolean cont  = true;	
				
		while( cont ) {
			
			System.out.println( "Menu Orgao: No que você quer mexer? ");
			System.out.println( " ");
			
			System.out.println( "1 - Insercao");
			System.out.println( "2 - Alteracao");
			System.out.println( "3 - Exclusao");
			System.out.println( "4 - Listagem");
			System.out.println( "5 - Sair");
			
			System.out.printf( '\n' + "Digite um número entre 1 e 5:");
			
			try {
				opt = sc.nextInt(  );
			}catch( InputMismatchException e ) {
				sc.nextLine();
				opt = sc.nextInt(  );
			}
			System.out.println("");
			
			switch( opt ) {
			
			case 1: 
				OrgaoMenu.inserirOrgao();
				break;
				
			case 2:
				OrgaoMenu.alterarOrgao();
				break;
				
			case 3:
				OrgaoMenu.excluirOrgao();
				break;
			
			case 4:
				OrgaoMenu.listarOrgao();
				break;
				
			case 5:
				cont = false;
				break;
				
			default:
				System.out.println( "Escolha inválida");
				
			}
		}
			
		}
	}

