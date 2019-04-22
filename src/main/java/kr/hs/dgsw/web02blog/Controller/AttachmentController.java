package kr.hs.dgsw.web02blog.Controller;

import kr.hs.dgsw.web02blog.Domain.*;
import kr.hs.dgsw.web02blog.Protocol.AttachmentProtocol;
import kr.hs.dgsw.web02blog.Repository.PostRepository;
import kr.hs.dgsw.web02blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
public class AttachmentController {

    @Autowired
    private UserRepository ur;

    @Autowired
    private PostRepository pr;

    @PostMapping("/attachment")
    public AttachmentProtocol upload(@RequestPart MultipartFile srcFile){
        String destFilename = "D:\\WorkSpace\\Java\\2019\\IdeaProjects\\web02blog\\upload\\"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern(("yyyy/MM/dd/")))
                + UUID.randomUUID().toString() + "_"
                + srcFile.getOriginalFilename();
        try {
            File destFile = new File(destFilename);
            destFile.getParentFile().mkdirs();
            srcFile.transferTo(destFile);
            return new AttachmentProtocol(srcFile.getOriginalFilename());
        } catch (IOException e) {
            return null;
        }
    }

    @GetMapping("/attachment/{type}/{id}")
    public void download(@PathVariable String type, @PathVariable Long id, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String filePath;
            if (type.equals("user")) {
                User user = ur.findById(id).orElse(null);
                filePath = user.getProfilePath();

            } else {
                Post post = pr.findById(id).orElse(null);
                filePath = post.getFilePath();
            }

            File file = new File(filePath);
            if (!file.exists()) return;

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) mimeType = "application.octet-stream";

            resp.setContentType(mimeType);
            resp.setHeader("Content-Disposition", "inline: filename=\"" + file.getName() + "\"");
            resp.setContentLength((int) file.length());

            InputStream is = null;

            is = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(is, resp.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
