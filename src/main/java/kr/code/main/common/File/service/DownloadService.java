package kr.code.main.common.File.service;

import kr.code.main.common.File.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadService {

    private final FileRepository fileRepository;

    public void incrementDownloadCount(String fileId) {
//        Download download = fileRepository.findById(fileId).orElse(null);
//        if (download == null) {
//            download = new Download(fileId, 1);
//        } else {
//            download.setDownloadCount(download.getDownloadCount() + 1);
//        }
//        fileRepository.save(download);
    }
}
