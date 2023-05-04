package kr.code.main.common.File.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter @Setter
@Table(name="tbl_file")
public class ManagedFile implements Serializable {

    @Id
    @Column(nullable = false)
    private String fileId;

    @Column(length = 10)
    private String fileOrder;

    @Column(length = 10)
    private String fileType;

    private String filePath;

    private String fileOriginName;

    private String fileStoredName;

    @Column(length = 10)
    private String fileExtsn;

    private Date uploadDate;

    private int downloadCount;

    @Builder
    public ManagedFile(String fileId, String fileOrder, String fileType, String filePath, String fileOriginName, String fileStoredName, String fileExtsn, int downloadCount) {
        this.fileId = fileId;
        this.fileOrder = fileOrder;
        this.fileType = fileType;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.fileStoredName = fileStoredName;
        this.fileExtsn = fileExtsn;
        this.downloadCount = downloadCount;
    }
}
