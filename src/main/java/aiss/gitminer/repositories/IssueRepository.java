package aiss.gitminer.repositories;

import aiss.gitminer.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findOneByState(String state);
    Optional<Issue> findByauthor_id(Long authorId);
}
