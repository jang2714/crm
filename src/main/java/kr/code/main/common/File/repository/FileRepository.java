package kr.code.main.common.File.repository;

import kr.code.main.common.File.domain.ManagedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<ManagedFile, Long> {

    List<ManagedFile> findAllByFileId(String fileId);

    void deleteAllByFileId(String fileId);
}
