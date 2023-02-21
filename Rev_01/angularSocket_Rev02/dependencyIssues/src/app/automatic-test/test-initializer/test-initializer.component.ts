import { Component, OnInit } from '@angular/core';
import { ProductLog } from 'src/app/models/product-log.model';
import { QrCode } from 'src/app/models/qr-code.model';
import { ResultLog } from 'src/app/models/result-log.model';
import { TagContainer } from 'src/app/models/tag-conteiner.model';
import { TestContainer } from 'src/app/models/test-conteiner.model';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import * as $ from "jquery";
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-test-initializer',
  templateUrl: './test-initializer.component.html',
  styleUrls: ['./test-initializer.component.css']
})
export class TestInitializerComponent implements OnInit {

  productQRCodeList: QrCode[] = []
  
  testContainers: TestContainer[] = []
  positionContainers: ProductLog[] = []
  resultContainers: ResultLog[] = []

  positionToShow: any = 1

  firstTest: boolean = true

  private serverUrl: string = 'http://localhost:8080/socket'
  private stompClient: any;

  constructor(private modalService: NgbModal) {
    this.initializeWebSocketConnection();
    this.initQRCode();
  }

  ngOnInit(): void {
    //@Todo: this data will be received from other places
    //QrCode initiation

    //Positions
    let product = new ProductLog()
    product.serial = "Serial1"
    product.material = "Material1"
    product.produto = "Produto1"
    product.descricao = "Desc1"
    product.position = 1
    this.positionContainers.push(product)
    //
    let product2 = new ProductLog()
    product2.serial = "Serial2"
    product2.material = "Material2"
    product2.produto = "Produto2"
    product2.descricao = "Desc2"
    product2.position = 2
    this.positionContainers.push(product2)


    //Steps
    let test = new TestContainer()
    test.testName = "Test1"
    test.testResult = "Result1"
    test.tagContainer = []
    this.testContainers.push(test)
    //
    let tagContainer2 = new TagContainer()
    tagContainer2.descricao = "Desc1"
    tagContainer2.errorMessage = "Err1"
    tagContainer2.log = "log1"
    tagContainer2.testResult = "Result1"
    this.testContainers[0].tagContainer.push(tagContainer2)


    let test2 = new TestContainer()
    test2.testName = "Test2"
    test2.testResult = "Result2"
    test2.tagContainer = []
    this.testContainers.push(test2)
    //
    let tagContainer3 = new TagContainer()
    tagContainer3.descricao = "Desc1"
    tagContainer3.errorMessage = "Err1"
    tagContainer3.log = "log1"
    tagContainer3.testResult = "Result1"
    this.testContainers[1].tagContainer.push(tagContainer3)
    //
    let tagContainer4 = new TagContainer()
    tagContainer4.descricao = "Desc2"
    tagContainer4.errorMessage = "Err2"
    tagContainer4.log = "log2"
    tagContainer4.testResult = "Result2"
    this.testContainers[1].tagContainer.push(tagContainer4)


    let test3 = new TestContainer()
    test3.testName = "Test3"
    test3.testResult = "Result3"
    //2) Iniciar array de Tags
    test3.tagContainer = []
    this.testContainers.push(test3)

    //Results
    let result = new ResultLog()
    result.action = "action1"
    result.finished = true
    result.log = "log1"
    result.position = 1
    result.result = "result1"
    result.status = "status1"
    this.resultContainers.push(result)
    //
    let result2 = new ResultLog()
    result2.action = "action2"
    result2.finished = true
    result2.log = "log2"
    result2.position = 2
    result2.result = "result2"
    result2.status = "status2"
    this.resultContainers.push(result2)


    this.testContainers = []
    this.positionContainers = []
    this.resultContainers = []
  }

  //@Todo: some spinner appearing logic

  //@Todo: disable second click
  public hideInitiateLayer(): void {
    const element1 = document.getElementById('overlay')
    const element2 = document.getElementById('overlay-background')
    if(element1!=null && element2!=null) {
      element1.style.opacity = "0"
      element2.style.opacity = "0"
      setTimeout(() => {
        element1.style.display = "none";
        element2.style.display = "none"
      }, 2000);
    }
  }

  public resetVariables(): void {
    this.testContainers = []
    this.positionContainers = []
    this.resultContainers = []
  }

  public initiateTest(): void {
    //@Todo: maybe use form, input is not able to get value
    //@Todo: Finish this method
    this.firstTest = false
    
    const element1 = document.getElementById('overlay')
    const element2 = document.getElementById('overlay-background')
    if(element1!=null && element2!=null) {
      element1.style.opacity = "0"
      element2.style.opacity = "0"
      setTimeout(() => {
        element1.style.display = "none"
        element2.style.display = "none"
      }, 600);
    }

    this.resetVariables()
    this.sendMessage()
  }

  public selectedPosition(position: any): void {
    this.positionToShow = position
  }

  public openQRCodeReceiver(): void {
    const element1 = document.getElementById('overlay')
    const element2 = document.getElementById('overlay-background')
    if(element1!=null && element2!=null) {
      element1.style.display = "inline"
      element2.style.display = "inline"
      setTimeout(() => {
        element1.style.opacity = "1"
        element2.style.opacity = "0.72"
      }, 1)
    }
  }

  initQRCode(): void {
    let code = new QrCode();
    code.position = 1;
    this.productQRCodeList.push(code);

    //let input = (<HTMLInputElement>document.getElementById("qrCode--1")).autofocus;
  }

  checkQRCode(id: string): void {
    //017890355940471 211033926936 10 911108232984
    let qrCodeSize = 44
    let qrCodeSize2 = 56
    let inputValue = (<HTMLInputElement>document.getElementById(id)).value
    
    if(inputValue.length==qrCodeSize || inputValue.length==qrCodeSize2) {
      let code = new QrCode()
      code.position = this.productQRCodeList.length+1
      this.productQRCodeList.push(code)

      //let input = (<HTMLInputElement>document.getElementById("qrCode-2")).autofocus;
    }
  }

  /*Socket configuration*/

  initializeWebSocketConnection(){  //@Todo: Clean up this function (maybe an array?)
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    
    this.stompClient.connect({}, function(frame: any) {
      that.stompClient.subscribe("/feedback2", (message: any) => {
        if(message.body) {  //fazer append de uma variável e utilizar o ngFor no html
          $(".chat").append("<div class='message'>"+/*JSON.parse(message.body)["cadastro"]*/message.body+"</div>")
          console.log(message.body);
        }
      });
    });
    
    let ws2 = new SockJS(this.serverUrl);
    let stompClient2 = Stomp.over(ws2);
    
    stompClient2.connect({}, function(frame: any) {
      stompClient2.subscribe("/channel", (message: any) => {  
        if(message.body) {
          let exp = "that."+JSON.parse(message.body).action+"(JSON.parse(message.body))"
          eval(exp)
        }
      });
    });
  }

  public cancelTest(): void {

  }

  stopRoutine(message: any):void {
    this.stompClient.send("/tester/stop" , {}, message);
    $('#stop').val('');
  }

  sendMessage(){
    let codeList: string[] = []
    let inputValue

    for (let productQRCode of this.productQRCodeList) {
      inputValue = (<HTMLInputElement>document.getElementById('qrCode-'+productQRCode.position)).value
      if(inputValue.length!=0) {
        codeList.push(inputValue)
      }
    }

    console.log(codeList.toString())

    this.stompClient.send("/tester/log", {}, codeList.toString());
  }

  starting(message: any):void {
    let productLog = new ProductLog()
    productLog.serial = message.serial
    productLog.material = message.material
    productLog.produto = message.produto
    productLog.descricao = message.descricao
    productLog.position = message.position
    this.positionContainers.push(productLog)
  }

  iniciar(message: any):void {
    let testContainer = new TestContainer()
    testContainer.testName = message.testName
    testContainer.testResult = message.testResult
    testContainer.tagContainer = []
    this.testContainers.push(testContainer)
  }

  exibir(message: any):void {
    for (let testContainer of this.testContainers) {
      if (testContainer.testName==message.testName) {
        let tagContainer = new TagContainer()
        tagContainer.descricao = message.descricao
        tagContainer.errorMessage = message.errorMessage
        tagContainer.log = message.log
        tagContainer.testResult = message.testResult
        testContainer.tagContainer.push(tagContainer)
      }
    }
  }

  finalizacao(message: any):void {
    let exist = false
    let position = 0
    let result = new ResultLog()
    result.action = message.action
    result.finished = message.finished
    result.log = message.log
    result.position = message.position
    result.result = message.result
    result.status = message.status
    
    this.resultContainers.forEach(function(item) {
      if (item.position==message.position) {
        position = item.position
        exist = true
      }
    })

    if(exist) {
      this.resultContainers[position-1] = result
    } else {
      this.resultContainers.push(result)
    }
  }


  modalText!: string
  //"confirmacao/input" receives demand and then "response/inputResponse" return value to the backend

  //@Todo: Refator a chamada de modals e etc
  confirmacao(textToDisplay: string):void {
    console.log("In here")
    let title = "Confirmação";
    let modalName = "confirmationModal";
    this.modalText = textToDisplay;
    (<HTMLButtonElement>document.getElementById('confirmationModalTrigger')).click()
  }

  response(message: boolean){
    this.stompClient.send("/tester/confirmation", {}, message);
  }

  input(textToDisplay: any):void {
    let title = "Confirmação";
    let modalName = "inputModal";
    this.modalText = textToDisplay;
    (<HTMLButtonElement>document.getElementById('inputModalTrigger')).click()
  }

  inputResponse(){
    this.stompClient.send("/tester/input", {}, this.answer);
  }

  //@Todo: maybe get ride of this global variable
  closeResult!: string
  answer!: string 
  open(modalName: any) {  
    this.modalService.open(modalName, {ariaLabelledBy: "Verificação"}).result.then((result) => {  
      this.closeResult = `Closed with: ${result}`;  
    }, (reason) => {  
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;  
    });  
  }
    
  private getDismissReason(reason: any): string {  
    if (reason === ModalDismissReasons.ESC) {  
      return 'by pressing ESC';  
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {  
      return 'by clicking on a backdrop';  
    } else {  
      return  `with: ${reason}`;  
    }  
  }
}