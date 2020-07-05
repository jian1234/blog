package com.blog.controller;

import com.blog.pojo.Blog;
import com.blog.pojo.Tag;
import com.blog.pojo.Type;
import com.blog.service.BlogService;
import com.blog.service.RedisService;
import com.blog.service.TagService;
import com.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

//    @Autowired
//    private RedisService redisService;
    @GetMapping("/")
    public String toIndex(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pagenum, Model model){

        PageHelper.startPage(pagenum, 5);
        //加redis
//        Object obj = redisService.get("IndexBlogKey");
//        List<Blog> allBlog = null;
//        if (obj == null){
//             allBlog = blogService.getIndexBlog();
//             redisService.set("IndexBlogKey",allBlog,604800);
//        }else{
//            allBlog = (List<Blog>) redisService.get("IndexBlogKey");
//        }
        List<Blog> allBlog = blogService.getIndexBlog();
        // 博客的类型
//        List<Type> allType = null;
//        Object objType = redisService.get("BlogTypeKey");
//        if (objType == null){
//            allType = typeService.getBlogType();  //获取博客的类型(联表查询)
//            redisService.set("BlogTypeKey",allType,604800);
//        }else{
//            allType = (List<Type>) redisService.get("BlogTypeKey");
//        }
        List<Type> allType = typeService.getBlogType();  //获取博客的类型(联表查询)
        //博客的标签
//        List<Tag> allTag = null;
//        Object objTag = redisService.get("BlogTagKey");
//        if (objTag == null){
//            allTag = tagService.getBlogTag();  //获取博客的标签(联表查询)
//            redisService.set("BlogTagKey",allTag,604800);
//        }else{
//            allTag = (List<Tag>) redisService.get("BlogTagKey");
//        }
        List<Tag> allTag = tagService.getBlogTag();  //获取博客的标签(联表查询)
        //推荐博客 先从redis查找
//        List<Blog> recommendBlog = null;
//        Object objrecommendBlog = redisService.get("RecommendBlogKey");
//        if (objrecommendBlog == null){
//            recommendBlog = blogService.getAllRecommendBlog();  //获取推荐博客
//            redisService.set("RecommendBlogKey",recommendBlog,604800);
//        }else{
//            recommendBlog = (List<Blog>) redisService.get("RecommendBlogKey");
//        }
        List<Blog> recommendBlog = blogService.getAllRecommendBlog();  //获取推荐博客
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendBlogs", recommendBlog);
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                         @RequestParam String query, Model model){

        PageHelper.startPage(pagenum, 5);
        List<Blog> searchBlog = blogService.getSearchBlog(query);
        PageInfo pageInfo = new PageInfo(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String toLogin(@PathVariable Long id, Model model){
        //加redis    key id
//        String detailkey = id+"detail";
//        Object obj = redisService.get(detailkey);
//        Blog blog = null;
//        if (obj == null){
//            blog  = blogService.getDetailedBlog(id);
//            redisService.set(detailkey,blog,604800);
//        }else{
//            System.out.println("博客详情");
//            blog = (Blog) redisService.get(detailkey);
//        }
        Blog blog  = blogService.getDetailedBlog(id);
        model.addAttribute("blog", blog);
        return "blog";
    }
}
