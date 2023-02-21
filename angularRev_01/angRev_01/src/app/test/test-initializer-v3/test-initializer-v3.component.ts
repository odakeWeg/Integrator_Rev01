import { TestModalComponent } from './../test-modal/test-modal.component';
import { Component, OnInit } from '@angular/core';
import { ProductLog } from 'src/app/models/models/product-log.model';
import { QrCode } from 'src/app/models/models/qr-code.model';
import { ResultLog } from 'src/app/models/models/result-log.model';
import { TestContainer } from 'src/app/models/models/test-conteiner.model';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import * as $ from "jquery";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TagContainer } from 'src/app/models/models/tag-conteiner.model';

@Component({
  selector: 'app-test-initializer-v3',
  templateUrl: './test-initializer-v3.component.html',
  styleUrls: ['./test-initializer-v3.component.css']
})
export class TestInitializerV3Component implements OnInit {

  productQRCodeList: QrCode[] = []
  
  testContainers: TestContainer[] = []
  positionContainers: ProductLog[] = []
  resultContainers: ResultLog[] = []

  positionToShow: any = 1

  firstTest: boolean = true

  serverUrl: string = 'http://localhost:8080/socket'
  stompClient: any;

  constructor(private modalService: NgbModal) { }

  ngOnInit(): void {
    this.initiateQrCode()
    this.initializeWebSocketConnection()

    this.openResultModal("message")
  }

  public initiateQrCode(): void {
    let code = new QrCode()
    this.productQRCodeList = []
    code.position = 1
    this.productQRCodeList.push(code)
  }

  public initiateTest(): void {
    this.firstTest = false;
    
    this.hideInitiateLayer()
    this.resetVariables()
    this.sendMessage()

    //document.getElementById('qrCode-'+this.productQRCodeList[i].position)
  }

  public resetVariables(): void {
    this.testContainers = []
    this.positionContainers = []
    this.resultContainers = []
  }

  public hideInitiateLayer(): void {//productQRCodeList
    const element1 = document.getElementById('overlay')
    const element2 = document.getElementById('overlay-background')
    if(element1!=null && element2!=null) {
      element1.style.opacity = "0"
      element2.style.opacity = "0"
      setTimeout(() => {
        element1.style.display = "none"
        element2.style.display = "none"
        this.initiateQrCode()
      }, 600);
    }
  }

  public unhideInitiateLayer(): void {
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

  public checkQRCodeInput(inputField: number): void {
    let qrCodeLength = 56
    let inputElement = <HTMLInputElement>document.getElementById("qrCode-"+inputField)
    if(inputElement.value.length==qrCodeLength) {
      inputElement.disabled = true
      this.setQRCode(inputElement.value)
      this.insertQRCode()
      setTimeout(()=>{
        inputElement = <HTMLInputElement>document.getElementById("qrCode-"+(inputField+1))
        inputElement.focus()
      },0);  
    }
  }

  public setQRCode(value: string): void {
    this.productQRCodeList[this.productQRCodeList.length-1].ProductQrCode = value
  }
  
  public insertQRCode(): void {
    let code = new QrCode()
    code.position = this.productQRCodeList.length+1
    this.productQRCodeList.push(code)
  }

  public cancelTest(): void {
    //@Todo
  }

  //WebSocket

  public initializeWebSocketConnection(): void {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    
    this.stompClient.connect({}, function(frame: any) {
      that.stompClient.subscribe("/feedback2", (message: any) => {
        if(message.body) {
          $(".chat").append("<div class='message'>"+message.body+"</div>")
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

  public sendMessage(): void{
    let codeList: string[] = []

    for (let productQRCode of this.productQRCodeList) {
      if(productQRCode.ProductQrCode!=null) {
        codeList.push(productQRCode.ProductQrCode)
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
    for (let test of this.testContainers) {
      if (test.testName==message.testName) {
        let tagContainer = new TagContainer()
        tagContainer.descricao = message.descricao
        tagContainer.errorMessage = message.errorMessage
        tagContainer.log = message.log
        tagContainer.testResult = message.testResult
        test.tagContainer.push(tagContainer)
      }
    }
    /*
    for (let i = 0; i < this.testContainers.length; i++) {
      if (this.testContainers[i].testName==message.testName) {
        let tagContainer = new TagContainer()
        tagContainer.descricao = message.descricao
        tagContainer.errorMessage = message.errorMessage
        tagContainer.log = message.log
        tagContainer.testResult = message.testResult
        this.testContainers[i].tagContainer.push(tagContainer)
      }
    }
    */
  }

  //@Todo Functions called by reflection
  finalizacao(message: any):void {
    //@Todo
    //$(".end").append("<div class='message'>Result: "+message.result+" Position: "+message.position+"</div>")
    console.log("Finalizacao");
    let exist = false
    let position = 0
    let result = new ResultLog()
    result.action = message.action
    result.finished = message.finished
    result.log = message.log
    result.position = message.position
    result.result = message.result
    result.status = message.status
    
    if(result.position!=0) {
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
    } else {
      this.openResultModal("this")
      if(this.positionContainers.length==0) {
        this.firstTest = true
      }
      //@Todo: Open an alert with the information
      //@Todo: Find for a way to check if the hardware is energized -> not a js problem
    }
  }

  openResultModal(result: any) {
    const modalRef = this.modalService.open(TestModalComponent)
    modalRef.componentInstance.result = result
  }

  stopRoutine():void {
    this.stompClient.send("/tester/stop" , {});
  }

}
