package com.meal.controller;


import com.meal.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class ImageController {

  private ImageService imageService;

  @Autowired
  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @RequestMapping(value = "/report/image/{id}", method = RequestMethod.POST, consumes = "multipart/form-data")
  public ResponseEntity uploadFile(@PathVariable(value = "id") int id,
                                   @RequestParam("file") MultipartFile image) {
    try {
      imageService.saveImage(3, image.getBytes());
    } catch (Exception e){

    }
    return new ResponseEntity(HttpStatus.OK);
  }

  @RequestMapping(value = "/report/image/{id}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE,
          MediaType.IMAGE_JPEG_VALUE})
  public ResponseEntity<byte[]> loadFile(@PathVariable(value = "id") int id) {
    byte[] image = imageService.findImage(id);
    return new ResponseEntity<byte[]>(image, HttpStatus.OK);
  }

}
