package uz.pdp.warehouse.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.warehouse.entity.Attachment;
import uz.pdp.warehouse.entity.AttachmentContent;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.AttachmentContentRepository;
import uz.pdp.warehouse.repository.AttachmentRepository;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public List<Attachment> getAttachments(){
        return attachmentRepository.findAll();
    }

    public void getFileById(Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.getByAttachment_Id(id);
            if (optionalAttachmentContent.isPresent()){
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                response.setHeader("Content-Disposition", "attachment; filename=\""+attachment.getName()+"\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
            }
        }
    }

    public Result uploadFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file!=null){
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            long size = file.getSize();
            Attachment attachment = new Attachment();
            attachment.setName(originalFilename);
            attachment.setContentType(contentType);
            attachment.setSize(size);
            Attachment attachment1 = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setAttachment(attachment1);
            attachmentContent.setBytes(file.getBytes());
            attachmentContentRepository.save(attachmentContent);
            return new Result("File saqlandi", true);
        }
        return new Result("Xatolik", false);
    }

    public Result deleteFile(Integer id){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.getByAttachment_Id(id);
            if (optionalAttachmentContent.isPresent()){
                attachmentContentRepository.delete(optionalAttachmentContent.get());
            }
            attachmentRepository.delete(optionalAttachment.get());
            return new Result("File o'chirildi!", true);
        }
        return new Result("File topilmadi!", false);
    }

}
