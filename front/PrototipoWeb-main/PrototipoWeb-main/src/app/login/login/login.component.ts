import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/shared';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @ViewChild('formUser') formCliente!: NgForm
  user!: User
  userReference!: User
  
  incorrect: boolean = false

  constructor(private loginService: LoginService, public router: Router) { }

  ngOnInit(): void {
    this.user = new User()
  }

  //@Todo: Melhorar a validaçao. Está dando Exception quando o cadastro nao existe e volta nulo
  login(): void {
    this.loginService.getUserByCadastro(this.user.cadastro!).subscribe(data => {
      this.userReference = data[0];
      console.log(this.userReference)
      console.log(this.user)
      if (this.formCliente.form.valid && this.loginService.isCredentialsValid(this.userReference!, this.user!)) {
        this.router.navigate(['home']);
      } else {
        this.incorrect = true
      }
    })
  }

}
