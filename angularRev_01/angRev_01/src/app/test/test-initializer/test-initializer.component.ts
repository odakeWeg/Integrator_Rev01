import { QrCode } from './../../models/models/qr-code.model';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ProductLog } from 'src/app/models/models/product-log.model';
import { ResultLog } from 'src/app/models/models/result-log.model';
import { TagContainer } from 'src/app/models/models/tag-conteiner.model';
import { TestContainer } from 'src/app/models/models/test-conteiner.model';

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

  constructor(private modalService: NgbModal) { }

  ngOnInit(): void {
    //@Todo: this data will be received from other places
    //QrCode initiation
    let code = new QrCode()
    code.position = 1
    this.productQRCodeList.push(code)
    //
    let code2 = new QrCode()
    code2.position = 2
    this.productQRCodeList.push(code2) 

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


    //this.testContainers = []
    //this.positionContainers = []
    //this.resultContainers = []

    let b
    b = (<HTMLButtonElement>document.getElementById('inputModalTrigger')).click()
    console.log("answer: " + this.answer)
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
    console.log("answer: " + this.answer)
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

    for (let i = 0; i < this.productQRCodeList.length; i++) {
      console.log('qrCode-'+this.productQRCodeList[i].position)
      console.log(document.getElementById('qrCode-'+this.productQRCodeList[i].position))
    }
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

  public cancelTest(): void {

  }

  //Changed for a possible bootstrap dropdown
  /*
  public toogleTestVisibility(test: TestContainer):void {
    if(test.testVisibility!=false) {
      test.testVisibility = true
    } else {
      test.testVisibility = false
    }
    
  }
  */

  

  closeResult!: string;  
  answer!: string;
  open(modalName: any) {  
    console.log("in open")
    this.modalService.open(modalName, {ariaLabelledBy: "title"}).result.then((result) => {  
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
