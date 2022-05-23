package modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table( name = "Produto")
@DynamicInsert
@DynamicUpdate
public class Produto {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "Numero" )
    private Long numero;
    
    @Column( name = "Nome")
    private String nome;
	
	@Column( name = "Descricao")
	private String descricao;
	
	@Column( name = "Medida")
	private Medida medida;
	
    @Version
    @Column( name = "Versao")
    private int versao;

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMedida() {
		return medida.name();
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public int getVersao() {
		return versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}

	@Override
	public String toString() {
//		return "Produto [numero=" + numero + ", nome=" + nome + ", descricao=" + descricao + ", medida=" + medida
//				+ ", versao=" + versao + "]";
		
		String s1 = "Produto #" + numero.toString() + "\n";
		String s2 = "Nome: " + nome + "\n";
		String s3 = "Medida: " +  medida.name() + "\n\n";
		
		String s4 = "Descricao:\n" + descricao + "\n\n";
		
		String s5 = "Versao #" + versao + "\n";
		String s6 = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
		
		return s1 + s2 + s3 +s4 +s5 + s6;
		
	}
    
    
    
}
