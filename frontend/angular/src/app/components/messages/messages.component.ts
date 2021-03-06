import {Component, OnInit, ViewChild} from '@angular/core';

import { UserService, AlertService } from '../../services/index';
import { User, Message } from '../../models/index';
import {MessageService} from "../../services/message/message.service";
import {ModalComponent} from "../modal/modal.component";
import {Observable} from "rxjs";
import {AnonymousSubscription} from "rxjs/Subscription";

@Component({
    moduleId: module.id,
    selector: 'messages',
    templateUrl: 'messages.component.html',
    styleUrls: ['messages.component.scss']
})

export class MessagesComponent {
    @ViewChild(ModalComponent)
    public readonly modal: ModalComponent;
    private timerSubscription: AnonymousSubscription;
    private currentUser: User;
    private receivers: User[] = []
    private newMessage: Message = new Message();
    private incomingMessages: any;
    private outgoingMessages: any;
    constructor(private userService: UserService, private alertService: AlertService, private messageService: MessageService) {
        this.currentUser = JSON.parse(localStorage.getItem("currentUser"));

        this.newMessage.sender.id = this.currentUser.id;
    }
    ngOnInit(){
        this.loadIncomingMessages();
        this.loadOutgoingMessages();
        this.loadUsers();
    }
    public ngOnDestroy(): void {
        if (this.timerSubscription) {
            this.timerSubscription.unsubscribe();
        }
    }

    private loadIncomingMessages() {
         this.messageService.getIncoming(this.currentUser.id).subscribe(
            data => {
                console.log(data);
                this.incomingMessages = data;
                this.subscribeToIncomingMessages();
            },
            error => {
                let msg = (error._body != '')? error._body : error;
                this.alertService.error('Ошибка. ' +msg);
            });
    }

    private subscribeToIncomingMessages(): void {
        this.timerSubscription = Observable.timer(3000).first().subscribe(() => this.loadIncomingMessages());
    }

    private loadOutgoingMessages() {
        this.messageService.getOutgoing(this.currentUser.id).subscribe(
            data => {
                console.log(data);
                this.outgoingMessages = data;

            },
            error => {
                let msg = (error._body != '')? error._body : error;
                this.alertService.error('Ошибка. ' +msg);
            });
    }
    onSelectNotification(selectedReceiver: number){
        this.newMessage.receiver.id = selectedReceiver;
        console.log('receiver id: ' + selectedReceiver);
    }

    private sendMessage(){
        this.messageService.create(this.newMessage).subscribe(
            data => {
                this.loadOutgoingMessages();
                this.alertService.success('Сообщение отправлено.');
                this.modal.hide();
            },
            error => {
                let msg = (error._body != '')? error._body : error;
                this.alertService.error('Ошибка. ' +msg);
            });
    }
    private loadUsers() {
        this.userService.getAll()
            .subscribe(users => {
                console.log('Loaded from service: ' + users);
                this.receivers = users;
            })
    }

}