package kr.code.main.common.File.service;

import kr.code.main.common.File.domain.ManagedFile;
import kr.code.main.common.File.repository.FileRepository;
import kr.code.main.utils.MediaUtils;
import kr.code.main.utils.UploadFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final UploadFileUtils fileUtils;

    public int AddManagedFile(String customerUid, String[] files, int filecnt) {

        List<ManagedFile> toAddFiles = new ArrayList<>();

        AtomicInteger index = new AtomicInteger();
        Arrays.stream(files).forEach( file -> {
            MediaType mediaType = MediaUtils.getMediaType(file);

            String extsn = file.substring(file.lastIndexOf(".") + 1 );
            String origin = file.substring( file.lastIndexOf("_") + 1 );
            String path = file.substring( file.lastIndexOf(File.separator) + 1 );
            String order = String.valueOf(index.getAndIncrement());

            ManagedFile managedFile = ManagedFile.builder()
                    .fileId(customerUid)
                    .fileType(mediaType.toString())
                    .fileExtsn(extsn)
                    .fileOriginName(origin)
                    .filePath(path)
                    .fileOrder(order)
                    .fileStoredName(file)
                    .build();

            toAddFiles.add(managedFile);
        });

        List<ManagedFile> savedFiles = fileRepository.saveAll(toAddFiles);
        System.out.println("고객 파일 등록 완료 : query(" + savedFiles.size() + ") input(" + filecnt + ").");

        return savedFiles.size();
    }

    public String[] GetManagedFileList(String customerUid) {

        List<ManagedFile> findedList = fileRepository.findAllByFileId(customerUid);

        return findedList.stream().map( file -> {
            return file.getFilePath() + file.getFileStoredName() + file.getFileExtsn();
        } ).toArray(String[]::new);
    }
}
