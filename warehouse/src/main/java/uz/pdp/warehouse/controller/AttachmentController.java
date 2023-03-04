package uz.pdp.warehouse.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.warehouse.entity.Attachment;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.service.AttachmentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @GetMapping
    public List<Attachment> getFiles(){
        return attachmentService.getAttachments();
    }

    @GetMapping("/{id}")
    public void getFileById(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        attachmentService.getFileById(id, response);
    }

    @PostMapping
    public Result uploadFile(MultipartHttpServletRequest request) throws IOException {
        return attachmentService.uploadFile(request);
    }

    @DeleteMapping("/{id}")
    public Result deleteFile(@PathVariable Integer id){
        return attachmentService.deleteFile(id);
    }

}
