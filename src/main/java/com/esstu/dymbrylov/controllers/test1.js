const animItems = document.querySelectorAll('._anim-items');
if (animItems.length > 0) {
    window.addEventListener('scroll', animOnScroll);
    function animOnScroll(){
        for (var i = 0; i < animItems.length; i++) {
            const animItem = animItems[i];
            const animItemHeight = animItem.offsetHeight;
            const animItemOffset = offset(animItem).top;
            const animStart = 4;
            let animItemPoint = window.innerHeight - animItemHeight/animStart;
            if (animItemHeight>window.innerHeight) {
                animItemPoint = window.innerHeight - window.innerHeight/animStart;
            }
            if ((pageYOffset > animItemOffset - animItemPoint) && (pageYOffset < animItemOffset + animItemHeight)) {
                animItem.classList.add('_active');
            }else{
                animItem.classList.remove('_active');
            }
        }
    }
    function offset(el){
        const rect = el.getBoundingClientRect(),
            scrollLeft = window.pageXOffset || document.documentElement.scrollLeft,
            scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        return { top: rect.top + scrollTop, left: rect.left + scrollLeft }
    }
    setTimeout(function() {
        animOnScroll();
    }, 1000);
}

ПРИЛОЖЕНИЕ В
Листинг файла PHP
<?php
    $name=htmlspecialchars(trim($_POST['name']));
$address=htmlspecialchars($_POST['address']);
$theme=htmlspecialchars($_POST['theme']);
$message=htmlspecialchars($_POST['message']);
$phone=$_POST['phone'];
$email=$_POST['email'];

require 'phpmailer/PHPMailer.php';
require 'phpmailer/SMTP.php';
require 'phpmailer/Exception.php';

$title = "Новый вопрос";
$body = "
    <b>Имя:</b> $name<br>
<b>Телефон:</b> $phone<br>
<b>Почта:</b> $email<br>
<b>Адрес:</b> $address<br><br>
<b>Тема:</b> $theme<br>
<b>Сообщение:</b> $message<br>
";

//Настройки PHPMailer
$mail = new PHPMailer\PHPMailer\PHPMailer();
try {
    $mail->isSMTP();
    $mail->CharSet = "UTF-8";
    $mail->SMTPAuth = true;
//$mail->SMTPDebug = 2;
    $mail->Debugoutput = function($str, $level) {$GLOBALS['status'][] = $str;};

// Настройки вашей почты
    $mail->Host = 'ssl://smtp.mail.ru'; // SMTP сервера вашей почты
    $mail->Username = 'kostya.salimov.99'; // Логин на почте
    $mail->Password = 'zanudacao322'; // Пароль на почте
    $mail->SMTPSecure = 'ssl';
    $mail->Port = 465;
    $mail->setFrom('kostya.salimov.99@mail.ru', 'Менеджер'); // Адрес самой почты и имя отправителя

// Получатель письма
    $mail->addAddress('kostya.salimov.99@mail.ru');

// Отправка сообщения
    $mail->isHTML(true);
    $mail->Subject = $title;
    $mail->Body = $body;


// Проверяем отравленность сообщения
    if ($mail->send()) {
        $result = "success";
        header('Location:index.php');

    }
    else {
        $result = "error";
    }
} catch (Exception $e) {
    $result = "error";
    $status = "Сообщение не было отправлено. Причина ошибки: {$mail->ErrorInfo}";
}