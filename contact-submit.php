<?php
declare(strict_types=1);

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('Location: /contact.html');
    exit;
}

function clean_input(string $value): string
{
    $value = trim($value);
    $value = str_replace(["\r", "\n"], ' ', $value);
    return mb_substr($value, 0, 2000);
}

$name = clean_input((string)($_POST['name'] ?? ''));
$email = clean_input((string)($_POST['email'] ?? ''));
$phone = clean_input((string)($_POST['phone'] ?? ''));
$feedback = trim((string)($_POST['feedback'] ?? ''));
$contentRequest = trim((string)($_POST['content_request'] ?? ''));
$support = trim((string)($_POST['support'] ?? ''));

if ($name === '' || $email === '' || !filter_var($email, FILTER_VALIDATE_EMAIL)) {
    header('Location: /contact.html?status=error');
    exit;
}

$sections = [];
$sections[] = "Name: " . $name;
$sections[] = "Email: " . $email;
$sections[] = "Phone: " . ($phone !== '' ? $phone : 'Not provided');
$sections[] = "";
$sections[] = "Feedback:";
$sections[] = ($feedback !== '' ? $feedback : 'Not provided');
$sections[] = "";
$sections[] = "Request to add content:";
$sections[] = ($contentRequest !== '' ? $contentRequest : 'Not provided');
$sections[] = "";
$sections[] = "Support request:";
$sections[] = ($support !== '' ? $support : 'Not provided');

$message = implode("\n", $sections);

$subject = 'Aricsso Kids Contact Form Submission';
$to = 'info@aricsso.com';
$headers = [
    'MIME-Version: 1.0',
    'Content-Type: text/plain; charset=UTF-8',
    'From: Aricsso Kids Contact <no-reply@aricsso.com>',
    'Reply-To: ' . $email
];

$sent = mail($to, $subject, $message, implode("\r\n", $headers));

if ($sent) {
    header('Location: /contact.html?status=ok');
    exit;
}

header('Location: /contact.html?status=error');
exit;
