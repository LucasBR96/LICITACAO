package dao;

import java.util.List;

import execao.EstadoDeObjetoObsoletoException;
import execao.OrgaoNaoEncontradoException;
import modelos.Orgao;

public interface OrgaoDAO {
	long inclui(Orgao umOrgao);

	void altera(Orgao umOrgao)
			throws OrgaoNaoEncontradoException, EstadoDeObjetoObsoletoException;

	void exclui(long id)
			throws OrgaoNaoEncontradoException;

	Orgao recuperaUmOrgao(long numero)
			throws OrgaoNaoEncontradoException;

	List<Orgao> recuperaOrgaos();
}