package br.pucrs.eduardocastro002.exemplo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AcervoRepoJdbcImpl implements IAcervoRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public AcervoRepoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
 }
 @Override
 public List<Livro> getLivros() {
    List<Livro> resp = this.jdbcTemplate.query("SELECT * FROM livros",
    (rs, rowNum) -> new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"),
    rs.getInt("ano")));
    return resp;
 }
 @Override
 public List<String> getAutores() {
    List<String> resp = this.jdbcTemplate.query("SELECT DISTINCT autor FROM livros",
    (rs, rowNum) -> rs.getString("autor"));
    return resp;
 }
 @Override
 public boolean removeLivro(int id) {
    String sql = "DELETE FROM livros WHERE id = " + id;
    this.jdbcTemplate.batchUpdate(sql);
    return true;
 }
 @Override
public boolean cadastraLivroNovo(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)";
        this.jdbcTemplate.update(sql, livro.getTitulo(), livro.getAutor(), livro.getAno());
        return true;
}
@Override
public List<Livro> getLivrosDoAutor(String autor) {
    List<Livro> resp = this.jdbcTemplate.query("SELECT * FROM livros WHERE autor = ?",
    (rs, rowNum) -> new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"),
    rs.getInt("ano")), autor);
    return resp;
}
@Override
public List<Livro> getLivrosDoAutor(String autor, int ano) {
    List<Livro> resp = this.jdbcTemplate.query("SELECT * FROM livros WHERE autor = ? AND ano = ?",
    (rs, rowNum) -> new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"),
    rs.getInt("ano")), autor, ano);
    return resp;
}
@Override
public List<String> getTitulos() {
    List<String> resp = this.jdbcTemplate.query("SELECT titulo FROM livros",
    (rs, rowNum) -> rs.getString("titulo"));
    return resp;
}
@Override
public Livro getLivroTitulo(String titulo) {
    List<Livro> resp = this.jdbcTemplate.query("SELECT * FROM livros WHERE titulo = ?",
    (rs, rowNum) -> new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"),
    rs.getInt("ano")), titulo);
    if (resp.size() > 0) {
        return resp.get(0);
    }
    return null;
}
}
