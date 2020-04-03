package br.com.treinaweb.springboot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.springboot.entidades.Aluno;
import br.com.treinaweb.springboot.repositorios.RepositorioAluno;
import br.com.treinaweb.springboot.repositorios.RepositorioInstituicao;

@Controller
@RequestMapping("/alunos")
public class AlunosController {

	@Autowired
	private RepositorioAluno repoAluno;

	@Autowired
	private RepositorioInstituicao repoInst;

	@GetMapping("/index")
	public ModelAndView index() {

		ModelAndView resultado = new ModelAndView("aluno/index");
		List<Aluno> alunos = repoAluno.findAll();
		resultado.addObject("alunos", alunos);
		return resultado;

	}

	@GetMapping("/inserir")
	public ModelAndView inserir() {

		ModelAndView resultado = new ModelAndView("aluno/inserir");
		resultado.addObject("aluno", new Aluno());
		resultado.addObject("instituicoes", repoInst.findAll());
		return resultado;

	}

	@PostMapping("/inserir")
	public String inserirPost(Aluno aluno) {

		repoAluno.save(aluno);

		return "redirect:/alunos/index";

	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		Aluno aluno = repoAluno.findOne(id);
		ModelAndView resultado = new ModelAndView("aluno/editar");
		resultado.addObject("aluno", aluno);
		resultado.addObject("instituicoes", repoInst.findAll());
		return resultado;
	}

	@PostMapping("/editar")
	public String editarPost(Aluno aluno) {
		repoAluno.save(aluno);
		return "redirect:/alunos/index";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		repoAluno.delete(id);
		return "redirect:/alunos/index";
	}

	@GetMapping({ "/buscar/{nome}", "/buscar" })
	public @ResponseBody List<Aluno> buscar(@PathVariable Optional<String> nome) {

		if (nome.isPresent()) {
			return repoAluno.findByNomeContaining(nome.get());
		} else {
			return repoAluno.findAll();
		}

	}

}
