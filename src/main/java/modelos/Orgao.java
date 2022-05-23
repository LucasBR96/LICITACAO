package modelos;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

// Dinamic inser e update são caracteristicas do Hybernate.
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table( name = "orgao")
@DynamicInsert
@DynamicUpdate
public class Orgao {
	
	//---------------------------------------------------------------------------------------------------------------
	// Oito caracteres do tipo digito ou
	// 2; 3 e 3 caracteres do tipo digito separados por underscore 
    public static String cepRegex = "(\\d{8}|\\d{2}\\_\\d{3}\\_\\d{3})";
    
    //-------------------------------------------------------------------------------------------------------------
    // Apenas caracteres do tipo word ou espaços repetidos ao menos uma vez
    public static String enderecoRegex = "^[\\w\\s]+$";

    // Aqui chave Primaria
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "Numero" )
    private Long numero;
    
    @Column( name = "Nome")
    private String nome;
    
    @Column( name = "Endereco")
    private String endereco;
    
    @Column( name = "Cep")
    private String cep;

    @Version
    @Column( name = "Versao")
    private int versao;
    
    public Orgao() {
    }

    public Orgao(String nome, String endereco, String cep) {

        this.nome = nome;
        this.endereco = endereco;
        this.cep = cep;
    }

    // #### Getters #### -------------------------------------------

    public String getCep() {
        return cep;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }
    
    public Long getNumero() {
    	return this.numero;
    }
    
    public int getVersao() {
		return versao;
	}


	// #### Setters #### -------------------------------------------
    public void setCep(String cep) throws IllegalArgumentException {

        Pattern cepPat = Pattern.compile(cepRegex);
        boolean correct = cepPat.matcher(cep).find();
        if (!correct)
            throw new IllegalArgumentException("O formato do cep esta errado");

        this.cep = cep;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) throws IllegalArgumentException {

//        Pattern cepPat = Pattern.compile(cepRegex);
//        boolean correct = cepPat.matcher(endereco).find();
//        if (!correct)
//            throw new IllegalArgumentException("O formato do endereco esta errado");

        this.endereco = endereco;
    }
    
    public void setVersao(int versao) {
    	this.versao = versao;
    }
    
    
    // ##### Outros Metodos -----------------------------------------------------
    
    @Override
    public String toString() {
    	
    	String sHead = "Orgao #" + this.getNumero().toString() + " -------------------------------------------------";

    	String sNome = "Nome: " + this.getNome();
    	String sEndereco = "Endereco: " + this.getEndereco();
    	String sCep= "Cep: " + this.getCep();
    	String sVersao = "Versao: " + this.getVersao();
    	
//    	StringBuilder builder = new StringBuilder();
//    	builder.
    	
    	return String.format( "%s %n%n %s %n %s %n %s %n %s",  sHead, sNome , sEndereco, sCep, sVersao );
    }
}