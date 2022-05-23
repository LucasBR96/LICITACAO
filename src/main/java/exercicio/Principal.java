package exercicio;


import java.util.List;
import java.util.Scanner;

import corejava.Console;
import menu.OrgaoMenu;

public class Principal{
	
	public static void main (String[] args) {
	
		Scanner sc = new Scanner( System.in );
		boolean cont  = true;
		{	
				
			while( cont ) {
				
				System.out.println( "Menu Principal: No que você quer mexer? ");
				System.out.println( " ");
				
				System.out.println( "1 - Orgao");
				System.out.println( "2 - Pedidos");
				System.out.println( "3 - Produto");
				System.out.println( "4 - Sair");
				
				System.out.printf( '\n' + "Digite um número entre 1 e 4:");
				int opt = sc.nextInt(  );
				
				switch( opt ) {
					
					case 1:
						OrgaoMenu.begin();
						break;
						
	//				case 2:
	//					PedidoMenu.begin();
	//					break;
	//				
	//				case 3:
	//					ProdutoMenu.begin();
	//					break;
					
					case 4:
						cont = false;
						break;
						
					default:
						
						if( ( opt ==2 ) || ( opt == 3 ) ) {
							System.out.println( "Em breve..." );
						}else {
							System.out.println( "Opcao invalida" );
						}
						
	//					System.out.println( "Opcao invalida" );
						
						break;
						
				}
				
			}
			
			sc.close();
		
		}
	}
}
