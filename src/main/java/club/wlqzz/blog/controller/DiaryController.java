package club.wlqzz.blog.controller;


import club.wlqzz.blog.pojo.Diary;
import club.wlqzz.blog.pojo.User;
import club.wlqzz.blog.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/diary")
    public String toDiaryPage(HttpSession session, Model model) throws Exception {
        if(session.getAttribute("loginUser")!=null) {
           List<Diary> diaryList= diaryService.selectAll();
           Collections.reverse(diaryList);
           model.addAttribute("diaryList",diaryList);
            return "diary";
        }
        return "login";
    }

    @GetMapping("/lookDiary/{id}")
    public String lookDiary(@PathVariable("id")Integer id,Model model) throws Exception {
        Diary diary=diaryService.selectDiary(id);
        model.addAttribute("diary",diary);
        return "lookDiary";
    }

    @GetMapping("/writeDiary")
    public String writeDiary(){
        return "user/writeDiary";
    }

    @PostMapping("/addDiary")
    public String addDiary(Diary diary,HttpSession session) throws Exception {
        User user= (User) session.getAttribute("loginUser");
        diary.setUserId(user.getId());
        diaryService.addDiary(diary);
        return "redirect:/diary";
    }

    @GetMapping("/editDiary/{id}")
    public String editDiary(@PathVariable("id") Integer id, Model model) throws Exception {
        Diary diary = diaryService.selectDiary(id);
        model.addAttribute("diary", diary);
        return "user/editDiary";
    }

    @PostMapping("/updateDiary")
    public String updateDiary(Diary diary) throws Exception {
        diaryService.updateDiary(diary);
        return "redirect:/article";
    }

    @GetMapping("/deleteDiary")
    public String deleteDiary(Diary diary) throws Exception {
        diaryService.deleteDiary(diary.getId());
        return "redirect:/article";
    }

    @GetMapping("/myDiary")
    public String myDiaryList(HttpSession session,Model model) throws Exception {
        User user = (User) session.getAttribute("loginUser");
        List<Diary>diaryList=diaryService.selectAllDiary(user.getId());
        Collections.reverse(diaryList);
        model.addAttribute("diaryList", diaryList);
        return "user/diaryList";
    }
}