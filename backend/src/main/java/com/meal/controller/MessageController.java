package com.meal.controller;

import com.meal.entity.MessageEntity;
import com.meal.entity.RoleEnum;
import com.meal.entity.UserEntity;
import com.meal.security.Secured;
import com.meal.service.MessageService;
import com.meal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MessageController {

  private final MessageService messageService;
  private final UserService userService;

  @Autowired
  public MessageController(MessageService messageService, UserService userService) {
    this.messageService = messageService;
    this.userService = userService;
  }

  /*
     CREATE MESSAGE
   */
  @Secured({RoleEnum.ADMIN, RoleEnum.COACH, RoleEnum.USER})
  @RequestMapping(value="/messages", method = RequestMethod.POST)
  public ResponseEntity<MessageEntity> createMessage(@RequestBody MessageEntity messageEntity) {
    messageService.createMessage(messageEntity);
    return new ResponseEntity(HttpStatus.OK);
  }

  /*
   GET OUTCOMING MESSAGES
  */
  @Secured({RoleEnum.ADMIN, RoleEnum.COACH, RoleEnum.USER})
  @RequestMapping(value="/messages/outgoing/{id}", method = RequestMethod.GET)
  public ResponseEntity<Iterable<MessageEntity>> getOutgoingMessages(@PathVariable(value = "id") int id,
                                                                     @RequestAttribute("user") UserEntity currentUser) {
    userService.hasPermission(id, currentUser, RoleEnum.USER);
    userService.hasPermission(id, currentUser, RoleEnum.COACH);
    Iterable<MessageEntity> messages = messageService.getMessagesBySenderId(id);
    return new ResponseEntity<Iterable<MessageEntity>>(messages, HttpStatus.OK);
  }

  /*
   GET ALL USER MESSAGES
  */
  @Secured({RoleEnum.ADMIN, RoleEnum.COACH, RoleEnum.USER})
  @RequestMapping(value="/messages/incoming/{id}", method = RequestMethod.GET)
  public ResponseEntity<Iterable<MessageEntity>> getIncomingMessages(@PathVariable(value = "id") int id,
                                                                     @RequestAttribute("user") UserEntity currentUser) {
    userService.hasPermission(id, currentUser, RoleEnum.USER);
    userService.hasPermission(id, currentUser, RoleEnum.COACH);
    Iterable<MessageEntity> messages = messageService.getMessagesByReceiverId(id);
    return new ResponseEntity<Iterable<MessageEntity>>(messages, HttpStatus.OK);
  }
  
}
