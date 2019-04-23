package kr.hs.dgsw.web02blog.Controller;

import kr.hs.dgsw.web02blog.Domain.*;
import kr.hs.dgsw.web02blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web02blog.Protocol.ResponseType;
import kr.hs.dgsw.web02blog.Repository.AttachmentRepository;
import kr.hs.dgsw.web02blog.Repository.PostRepository;
import kr.hs.dgsw.web02blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
public class AttachmentController {

    @Autowired
    private AttachmentRepository ar;

    @Autowired
    private UserRepository ur;

    @Autowired
    private PostRepository pr;

    @PostConstruct
    private void init(){
        ur.save(new User("a", "123", "test"));
        pr.save(new Post(1L,"123","123"));
        pr.save(new Post(1L,"123","123"));
        pr.save(new Post(1L,"123","123"));
        ar.save(new Attachment("D:\\c0062637_4b2e3fdb6363b.jpg", 1L));
    }

    @PostMapping("/attachment/{type}/{id}")
    public ResponseFormat upload(@PathVariable String type, @PathVariable Long id,
                                 @RequestPart MultipartFile srcFile){
        String destFilename = "D:\\WorkSpace\\Java\\2019\\IdeaProjects\\web02blog\\upload\\"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern(("yyyy/MM/dd/")))
                + UUID.randomUUID().toString() + "_"
                + srcFile.getOriginalFilename();
        try {
            File destFile = new File(destFilename);
            destFile.getParentFile().mkdirs();
            srcFile.transferTo(destFile);
            Attachment a = new Attachment();
            a.setFilePath(destFilename);
            if (type.equals("user")) a.setUserId(id);
            else a.setPostId(id);
            return new ResponseFormat(ResponseType.ATTACHMENT_STORED, this.ar.save(a));
        } catch (IOException e) {
            return null;
        }
    }

    @GetMapping("/attachment/{type}/{id}")
    public void download(@PathVariable String type, @PathVariable Long id, HttpServletRequest req, HttpServletResponse resp) {
        try {
            Attachment filePath;
            if (type.equals("user")) {
                User user = ur.findById(id).orElse(null);
                filePath = user.getProfile();
                if (fileResp(resp, filePath)) return;

            } else {
                Post post = pr.findById(id).orElse(null);
                List<Attachment> pictures = post.getPictures();
                for(int i=0; i< pictures.size(); i++){
                    filePath = pictures.get(i);
                    if (fileResp(resp, filePath)) return;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean fileResp(HttpServletResponse resp, Attachment filePath) throws IOException {
        File file = new File(filePath.getFilePath());
        if (!file.exists()) return true;

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) mimeType = "application.octet-stream";

        resp.setContentType(mimeType);
        resp.setHeader("Content-Disposition", "inline: filename=\"" + file.getName() + "\"");
        resp.setContentLength((int) file.length());

        InputStream is = null;

        is = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(is, resp.getOutputStream());
        return false;
    }
}
